package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments

class StringValue(value: String) : Value<String>(value) {
    override fun typeString(): String = "string"

    override fun toString(): String {
        return "\"" + value + "\""
    }

    override fun plus(other: String): Value<*> {
        return StringValue(value + other)
    }

    override fun iteratorGet(index: Int): Value<*> {
        return StringValue(value[index].toString())
    }

    override fun iteratorSize(): Int {
        return value.length
    }

    fun wrapIndex(index: Int): Int {
        var index = index

        if (index >= value.length) {
            throw RunException("Index " + index + " out of range for string of length " + value.length)
        }

        while (index < 0) {
            index += value.length
        }

        return index
    }

    fun asIndex(item: Value<*>): Int {
        return wrapIndex(item.cast<IntegerValue>().value)
    }

    override fun get(item: Value<*>): Value<*> {
        return StringValue(value[asIndex(item)].toString())
    }

    override fun getFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        return when (name) {
            "contains" -> ::contains
            "uppercase" -> ::uppercase
            "lowercase" -> ::lowercase
            "length" -> ::length
            "substring" -> ::substring
            else -> super.getFunction(name)
        }
    }

    override fun contains(item: Value<*>): Boolean {
        return value.contains(item.cast<StringValue>().value)
    }

    fun contains(program: Program, arguments: Arguments): Value<*> {
        val item = arguments.get<StringValue>(program, "value").value
        return BooleanValue(value.contains(item))
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
}
