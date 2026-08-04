package program.expression.value

import program.Program
import program.expression.Arguments

class StringValue(value: String) : Value<String>(value) {
    override fun typeString(): String = "string"

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

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        return when (name) {
            "contains" -> contains(program, arguments)
            "uppercase" -> uppercase(program, arguments)
            "lowercase" -> lowercase(program, arguments)
            "length" -> length(program, arguments)
            "substring" -> substring(program, arguments)
            "lines" -> lines(program, arguments)
            "reversed" -> reversed(program, arguments)
            else -> super.innerCallFunction(program, arguments, name, local)
        }
    }

    fun contains(program: Program, arguments: Arguments): Value<*> {
        val item = arguments.get<StringValue>(program, "value").value
        val ignoreCase = arguments.get<BooleanValue>(program, "ignoreCase", BooleanValue(false)).value
        return BooleanValue(value.contains(item, ignoreCase))
    }

    fun uppercase(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return StringValue(value.uppercase())
    }

    fun lowercase(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return StringValue(value.lowercase())
    }

    fun length(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return IntegerValue(value.length)
    }

    fun substring(program: Program, arguments: Arguments): Value<*> {
        val start = arguments.get<IntegerValue>(program, "start").value
        val end = arguments.get<IntegerValue>(program, "end").value
        return StringValue(value.substring(start, end))
    }

    fun lines(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        val lines: MutableList<Value<*>> = value.lines().map { string -> StringValue(string) }.toMutableList()
        return ListValue(lines)
    }

    fun reversed(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return StringValue(value.reversed())
    }
}
