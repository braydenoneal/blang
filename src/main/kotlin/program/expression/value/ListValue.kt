package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments

class ListValue(value: MutableList<Value<*>>) : Value<MutableList<Value<*>>>(value) {
    override fun typeString(): String = "list"

    override fun toString(): String {
        val print = StringBuilder("[")

        for (i in value.indices) {
            print.append(value[i].toString())

            if (i < value.size - 1) {
                print.append(", ")
            }
        }

        return "$print]"
    }

    fun wrapIndex(index: Int): Int {
        var index = index

        if (index >= value.size) {
            throw RunException("Index " + index + " out of range for list of size " + value.size)
        }

        while (index < 0) {
            index += value.size
        }

        return index
    }

    fun asIndex(item: Value<*>): Int {
        return wrapIndex(item.cast<IntegerValue>().value)
    }

    override fun get(item: Value<*>): Value<*> {
        return value[asIndex(item)]
    }

    override fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        value[asIndex(item)] = setValue
        return setValue
    }

    override fun getItem(name: String): Value<*> {
        return when (name) {
            "size" -> IntegerValue(value.size)
            else -> super.getItem(name)
        }
    }

    override fun iteratorGet(index: Int): Value<*> {
        return value[wrapIndex(index)]
    }

    override fun iteratorSize(): Int {
        return value.size
    }

    override fun getFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        return when (name) {
            "append" -> ::append
            "containsAll" -> ::containsAll
            "contains" -> ::contains
            "insert" -> ::insert
            "pop" -> ::pop
            "remove" -> ::remove
            else -> super.getFunction(name)
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

        throw RunException("Expression is not a list")
    }

    override fun contains(item: Value<*>): Boolean {
        return value.contains(item)
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

    override fun plus(other: MutableList<Value<*>>): Value<*> {
        return ListValue(value.plus(other).toMutableList())
    }
}
