package program.expression.value

class StructValue(value: MutableList<Pair<String, Value<*>>>) : Value<MutableList<Pair<String, Value<*>>>>(value) {
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

    fun get(property: String): Value<*> {
        for (entry in value) {
            if (entry.first == property) {
                return entry.second
            }
        }

        return Null.VALUE
    }

    fun set(property: String, setValue: Value<*>): Value<*> {
        for (i in value.indices) {
            if (value[i].first == property) {
                value[i] = Pair(value[i].first, setValue)
                return setValue
            }
        }

        return Null.VALUE
    }
}
