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

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return when (name) {
            "size" -> IntegerValue(value.size)
            else -> super.getItem(name)
        }
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return when (name) {
            "append" -> append()
            "containsAll" -> containsAll()
            "contains" -> contains()
            "insert" -> insert()
            "pop" -> pop()
            "remove" -> remove()
            "reversed" -> reversed()
            "reverse" -> reverse()
            else -> super.innerCallFunction(name, local)
        }
    }

    context(program: Program, arguments: Arguments)
    fun append(): Value<*> {
        value.add(getAny("value"))
        return this
    }

    context(program: Program, arguments: Arguments)
    fun containsAll(): Value<*> {
        val nextListValue = getAny("value")

        if (nextListValue is ListValue) {
            return BooleanValue(value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list", span)
    }

    context(program: Program, arguments: Arguments)
    fun contains(): Value<*> {
        return BooleanValue(value.contains(getAny("value")))
    }

    context(program: Program, arguments: Arguments)
    fun insert(): Value<*> {
        val index = get<IntegerValue>("index").value
        val insertValue = getAny("value")
        value.add(index, insertValue)
        return this
    }

    fun pop(): Value<*> {
        value.removeLast()
        return this
    }

    context(program: Program, arguments: Arguments)
    fun remove(): Value<*> {
        val removeValue = getAny("value")

        if (removeValue is IntegerValue) {
            value.removeAt(removeValue.value)
        } else {
            value.remove(removeValue)
        }

        return this
    }

    fun reversed(): Value<*> {
        return ListValue(value.reversed().toMutableList())
    }

    fun reverse(): Value<*> {
        value.reverse()
        return this
    }

    override fun plus(other: MutableList<Value<*>>): Value<*> {
        return ListValue(value.plus(other).toMutableList())
    }
}
