package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments

class ListValue(value: MutableList<Value<*>>) : Value<MutableList<Value<*>>>(value) {
    override fun typeString(): String = "list"

    override fun toString(): String {
        return "[${value.joinToString()}]"
    }

    override fun toList(): List<Value<*>> {
        return value
    }

    override fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        value[asIndex(item)] = setValue
        return setValue
    }

    override fun getItem(program: Program, name: String): Value<*> {
        return when (name) {
            "size" -> IntegerValue(value.size)
            else -> super.getItem(program, name)
        }
    }

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        return when (name) {
            "append" -> append(program, arguments)
            "containsAll" -> containsAll(program, arguments)
            "contains" -> contains(program, arguments)
            "insert" -> insert(program, arguments)
            "pop" -> pop(program, arguments)
            "remove" -> remove(program, arguments)
            "reversed" -> reversed(program, arguments)
            "reverse" -> reverse(program, arguments)
            else -> super.innerCallFunction(program, arguments, name, local)
        }
    }

    fun append(program: Program, arguments: Arguments): Value<*> {
        value.add(arguments.getAny(program, "value"))
        return this
    }

    fun containsAll(program: Program, arguments: Arguments): Value<*> {
        val nextListValue = arguments.getAny(program, "value")

        if (nextListValue is ListValue) {
            return BooleanValue(value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list", span)
    }

    fun contains(program: Program, arguments: Arguments): Value<*> {
        return BooleanValue(value.contains(arguments.getAny(program, "value")))
    }

    fun insert(program: Program, arguments: Arguments): Value<*> {
        val index = arguments.get<IntegerValue>(program, "index").value
        val insertValue = arguments.getAny(program, "value")
        value.add(index, insertValue)
        return this
    }

    fun pop(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        value.removeLast()
        return this
    }

    fun remove(program: Program, arguments: Arguments): Value<*> {
        val removeValue = arguments.getAny(program, "value")

        if (removeValue is IntegerValue) {
            value.removeAt(removeValue.value)
        } else {
            value.remove(removeValue)
        }

        return this
    }

    fun reversed(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return ListValue(value.reversed().toMutableList())
    }

    fun reverse(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        value.reverse()
        return this
    }

    override fun plus(other: MutableList<Value<*>>): Value<*> {
        return ListValue(value.plus(other).toMutableList())
    }
}
