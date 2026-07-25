package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Null

class StructValue(value: MutableList<Pair<String, Value<*>>>) : Value<MutableList<Pair<String, Value<*>>>>(value) {
    override fun typeString(): String = "struct"

    override fun toString(): String {
        val print = StringBuilder("{")

        for (i in value.indices) {
            print.append(value[i].first + ": " + value[i].second)

            if (i < value.size - 1) {
                print.append(", ")
            }
        }

        return "$print}"
    }

    fun getProperty(property: String): Value<*> {
        for ((first, second) in value) {
            if (first == property) {
                return second
            }
        }

        return Null.VALUE
    }

    fun setProperty(property: String, setValue: Value<*>): Value<*> {
        for (i in value.indices) {
            if (value[i].first == property) {
                value[i] = value[i].first to setValue
                return setValue
            }
        }

        return Null.VALUE
    }

    override fun getItem(name: String): Value<*> {
        val item = getProperty(name)

        if (item == Null.VALUE) {
            return super.getItem(name)
        }

        return item
    }

    override fun getFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        return when (name) {
            "entries" -> ::entries
            "keys" -> ::keys
            "remove" -> ::remove
            "values" -> ::values
            else -> super.getFunction(name)
        }
    }

    fun entries(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for ((first, second) in value) {
            list.add(StructValue(mutableListOf("key" to StringValue(first), "value" to second)))
        }

        return ListValue(list)
    }

    fun keys(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for (entry in value) {
            list.add(StringValue(entry.first))
        }

        return ListValue(list)
    }

    fun remove(program: Program, arguments: Arguments): Value<*> {
        val removeValue = arguments.getAny(program, "value")

        if (removeValue is StringValue) {
            for (i in value.indices) {
                if (value[i].first == removeValue.value) {
                    value.removeAt(i)
                }
            }
        }

        return this
    }

    fun values(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for (entry in value) {
            list.add(entry.second)
        }

        return ListValue(list)
    }
}
