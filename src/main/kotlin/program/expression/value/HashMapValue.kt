package program.expression.value

// The value parameter type -> Java variable to be wrapped
class HashMapValue(value: HashMap<Value<*>, Value<*>>) : Value<HashMap<Value<*>, Value<*>>>(value) {
    override fun toString(): String {
        val mapString = StringBuilder("{")
        for ((k, v) in value) {
            mapString.append(("($k: $v)"))
        }
        return "$mapString}"
    }

    fun get(item: Value<*>): Value<*> {
        return Null.VALUE
    }

    fun set(property: Value<*>, setValue: Value<*>): Value<*> {
        return Null.VALUE
    }
}
