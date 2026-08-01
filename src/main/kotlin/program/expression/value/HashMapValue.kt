package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Null

// The value parameter type -> Java variable to be wrapped
class HashMapValue(value: HashMap<Value<*>, Value<*>>) : Value<HashMap<Value<*>, Value<*>>>(value) {
    override fun typeString(): String = "hashMap"

    override fun toString(): String {
        val mapString = StringBuilder("{")
        for ((k, v) in value) {
            mapString.append(("($k: $v)"))
        }
        return "$mapString}"
    }

    override fun get(item: Value<*>): Value<*> {
        return value[item]?: Null.VALUE
    }

    override fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        value[item] = setValue
        return setValue
    }

    override fun getFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        return when (name) {
            "remove" -> ::remove
            "keys" -> ::keys
            "values" -> ::values
            "items" -> ::items
            else -> super.getFunction(name)
        }
    }
    fun remove(program: Program, arguments: Arguments): Value<*> {
        val removeValue = arguments.getAny(program, "value")
        value.remove(removeValue)
        return this
    }

    fun keys(program: Program, arguments: Arguments): Value<*> {

       return ListValue(value.keys.toMutableList())
    }
    fun values(program: Program, arguments: Arguments): Value<*> {
        return ListValue(value.values.toMutableList())
    }
    fun items(program: Program, arguments: Arguments): Value<*> {
        return ListValue(value.entries.toList().map{ PairValue(it.toPair())}.toMutableList())
    }

}
