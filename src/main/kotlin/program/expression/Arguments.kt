package program.expression

import parser.Parser
import parser.tokenizer.Span
import program.Program
import program.RunException
import program.expression.value.Value

class Arguments(
    val namelessArguments: MutableList<Expression>,
    val namedArguments: MutableMap<String, Expression>,
    var index: Int = 0,
    var computed: MutableMap<String, Value<*>> = mutableMapOf(),
    var counter: Int = 0,
    var hasSelf: Boolean = false,
) {
    var span = Span.NONE

    fun withSpan(start: Int, parser: Parser): Arguments {
        span = Span(start, parser.spanEnd())
        return this
    }

    context(program: Program)
    fun getAnyNullable(name: String): Value<*>? {
        if (computed.containsKey(name)) {
            return computed[name]!!
        }

        if (namelessArguments.size > index) {
            val value = namelessArguments[index].evaluate()
            index++
            computed[name] = value
            return value
        }

        for (argument in namedArguments) {
            if (argument.key == name) {
                val value = argument.value.evaluate()
                computed[name] = value
                return value
            }
        }

        return null
    }

    context(program: Program)
    fun getAny(name: String, default: Value<*>? = null): Value<*> {
        val value = getAnyNullable(name)

        if (value != null) {
            return value
        }

        if (default == null) {
            throw RunException("Missing argument $name", span)
        }

        computed[name] = default
        return default
    }

    context(program: Program)
    inline fun <reified T : Value<*>> get(name: String, default: T? = null): T {
        return getAny(name, default).cast<T>()
    }

    context(program: Program)
    inline fun <reified T : Value<*>> getNullable(name: String): T? {
        return getAnyNullable(name)?.cast<T>()
    }

    fun abort() {
        index = 0
    }

    fun done() {
        index = 0
        counter = 0
        computed.clear()
    }

    override fun toString(): String {
        val strings = mutableListOf<String>()

        for (argument in namelessArguments) {
            strings.add(argument.toString())
        }

        for ((name, _) in namedArguments) {
            strings.add(name)
        }

        return strings.joinToString(", ")
    }
}
