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

    fun get(index: Value<*>): Value<*> {
        if (index !is IntegerValue) {
            throw RunException("Index is not an integer")
        }

        if (index.value >= value.size) {
            throw RunException("Index " + index.value + " out of range for list of size " + value.size)
        }

        return value[index.value]
    }

    fun set(index: Value<*>, setValue: Value<*>): Value<*> {
        if (index !is IntegerValue) {
            throw RunException("Index is not an integer")
        }

        if (index.value >= value.size) {
            throw RunException("Index " + index.value + " out of range for list of size " + value.size)
        }

        value[index.value] = setValue
        return setValue
    }
}
