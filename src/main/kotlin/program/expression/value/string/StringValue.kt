package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value

class StringValue(value: String) : Value<String>(value) {
    override fun toString(): String {
        return "\"$value\""
    }

    override fun plus(other: String): Value<*> {
        return StringValue(value + other)
    }

    override fun toList(): List<Value<*>> {
        return value.map { StringValue(it.toString()) }.toList()
    }

    override fun fromList(list: List<Value<*>>): Value<*> {
        return StringValue(list.joinToString("") { it.cast<StringValue>().value })
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<StringValue>() {
        override val name = "String"

        override fun initializeFunctions() {
            register(ContainsFunction)
            register(UppercaseFunction)
            register(LowercaseFunction)
            register(LengthFunction)
            register(SubstringFunction)
            register(LinesFunction)
            register(ReversedFunction)
        }
    }
}
