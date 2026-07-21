package program.expression

import program.Program
import program.RunException
import program.expression.value.Value

data class Arguments(
    val namelessArguments: MutableList<Expression>,
    val namedArguments: MutableMap<String, Expression>,
    var index: Int = 0,
    var computed: MutableMap<String, Value<*>> = mutableMapOf(),
) {
    fun getAny(program: Program, name: String, default: Value<*>? = null): Value<*> {
        if (computed.containsKey(name)) {
            return computed[name]!!
        }

        if (namelessArguments.size > index) {
            val value = namelessArguments[index++].evaluate(program)
            computed[name] = value
            return value
        }

        for (argument in namedArguments) {
            if (argument.key == name) {
                val value = argument.value.evaluate(program)
                computed[name] = value
                return value
            }
        }

        if (default == null) {
            throw RunException("Missing argument $name")
        }

        computed[name] = default
        return default
    }

    inline fun <reified T : Value<*>> get(program: Program, name: String, default: T? = null): T {
        return getAny(program, name, default).cast<T>()
    }

    fun abort() {
        index = 0
    }

    fun done() {
        index = 0
        computed.clear()
    }

    companion object {
        val EMPTY: Arguments = Arguments(mutableListOf(), mutableMapOf())
    }
}
