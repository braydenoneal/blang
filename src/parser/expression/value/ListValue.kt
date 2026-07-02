package parser.expression.value

import parser.Program
import parser.RunException
import parser.expression.Expression

class ListValue(value: MutableList<Value<*>>) : Value<MutableList<Value<*>>>(value) {
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

    fun get(indexValues: List<Value<*>>): Value<*> {
        if (indexValues.isEmpty()) {
            throw RunException("No indices provided")
        }

        var currentValue: Value<*> = this

        for (index in indexValues) {
            if (index !is IntegerValue) {
                throw RunException("Index is not an integer")
            }

            if (currentValue !is ListValue) {
                throw RunException("Object is not a list")
            }

            if (index.value >= currentValue.value.size) {
                throw RunException("Index " + index.value + " out of range for list of size " + currentValue.value.size)
            }

            currentValue = currentValue.value[index.value]
        }

        return currentValue
    }

    fun set(indexValues: List<Value<*>>, value: Value<*>): Value<*> {
        val list = get(indexValues.subList(0, indexValues.size - 1))
        val lastIndex = indexValues.last()

        if (lastIndex !is IntegerValue) {
            throw RunException("Index is not an integer")
        }
        if (list !is ListValue) {
            throw RunException("Object is not a list")
        }

        list.value[lastIndex.value] = value
        return value
    }

    companion object {
        fun toIndexValues(program: Program, expressions: MutableList<Expression>): MutableList<Value<*>>? {
            val indices: MutableList<Value<*>> = ArrayList()

            for (expression in expressions) {
                indices.add(expression.evaluate(program) ?: return null)
            }

            return indices
        }
    }
}
