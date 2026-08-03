package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments

class MapValue(value: MutableMap<Value<*>, Value<*>>) : Value<MutableMap<Value<*>, Value<*>>>(value) {
    override fun typeString(): String = "map"

    override fun toString(): String {
        if (value.isEmpty()) {
            return "{}"
        }

        return "{ ${value.entries.joinToString { "${it.key}: ${it.value}" }} }"
    }

    override fun get(item: Value<*>): Value<*> {
        return value[item] ?: throw RunException("Value does not have item $item")
    }

    override fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        value[item] = setValue
        return setValue
    }

    override fun toList(): List<Value<*>> {
        return value.entries.map { PairValue(it.toPair()) }
    }

    override fun slice(from: Value<*>?, to: Value<*>?): Value<*> {
        throw RunException("Maps cannot be sliced")
    }

    override fun getFunction(program: Program, name: String): ((Program, Arguments) -> Value<*>)? {
        return when (name) {
            "keys" -> ::keys
            "values" -> ::values
            "entries" -> ::entries
            else -> super.getFunction(program, name)
        }
    }

    fun keys(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return ListValue(value.keys.toMutableList())
    }

    fun values(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return ListValue(value.values.toMutableList())
    }

    fun entries(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return ListValue(toList().toMutableList())
    }

    companion object : Static {
        override val name: String = "Map"

        override fun constructor(program: Program, arguments: Arguments): Value<*> {
            val map = mutableMapOf<Value<*>, Value<*>>()

            for (pair in arguments.get<ListValue>(program, "pairs").value) {
                val (first, second) = pair.cast<PairValue>().value
                map[first] = second
            }

            return MapValue(map)
        }
    }
}
