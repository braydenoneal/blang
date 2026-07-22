package program.expression.value

import program.RunException

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

    fun wrap(index: Value<*>): Int {
        var index = index.cast<IntegerValue>().value

        if (index >= value.size) {
            throw RunException("Index " + index + " out of range for list of size " + value.size)
        }

        while (index < 0) {
            index += value.size
        }

        return index
    }

    fun get(index: Value<*>): Value<*> {
        return value[wrap(index)]
    }

    fun set(index: Value<*>, setValue: Value<*>): Value<*> {
        value[wrap(index)] = setValue
        return setValue
    }
}
