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

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return when (name) {
            "contains" -> contains()
            "uppercase" -> uppercase()
            "lowercase" -> lowercase()
            "length" -> length()
            "substring" -> substring()
            "lines" -> lines()
            "reversed" -> reversed()
            else -> super.innerCallFunction(name, local)
        }
    }

    context(program: Program, arguments: Arguments)
    fun contains(): Value<*> {
        val item = arguments.get<StringValue>("value").value
        val ignoreCase = arguments.get<BooleanValue>("ignoreCase", BooleanValue(false)).value
        return BooleanValue(value.contains(item, ignoreCase))
    }

    fun uppercase(): Value<*> {
        return StringValue(value.uppercase())
    }

    fun lowercase(): Value<*> {
        return StringValue(value.lowercase())
    }

    fun length(): Value<*> {
        return IntegerValue(value.length)
    }

    context(program: Program, arguments: Arguments)
    fun substring(): Value<*> {
        val start = arguments.get<IntegerValue>("start").value
        val end = arguments.get<IntegerValue>("end").value
        return StringValue(value.substring(start, end))
    }

    fun lines(): Value<*> {
        val lines: MutableList<Value<*>> = value.lines().map { string -> StringValue(string) }.toMutableList()
        return ListValue(lines)
    }

    fun reversed(): Value<*> {
        return StringValue(value.reversed())
    }
}
