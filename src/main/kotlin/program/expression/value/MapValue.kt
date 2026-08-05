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

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return when (name) {
            "keys" -> keys()
            "values" -> values()
            "entries" -> entries()
            else -> super.innerCallFunction(name, local)
        }
    }

    fun keys(): Value<*> {
        return ListValue(value.keys.toMutableList())
    }

    fun values(): Value<*> {
        return ListValue(value.values.toMutableList())
    }

    fun entries(): Value<*> {
        return ListValue(toList().toMutableList())
    }

    companion object : Static {
        override val name: String = "Map"

        context(program: Program, arguments: Arguments)
        override fun innerCall(): Value<*> {
            val map = mutableMapOf<Value<*>, Value<*>>()

            for (pair in get<ListValue>("pairs").value) {
                val (first, second) = pair.cast<PairValue>().value
                map[first] = second
            }

            return MapValue(map)
        }
    }
}
