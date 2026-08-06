package program.expression.value.map

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value
import program.expression.value.get
import program.expression.value.list.ListValue
import program.expression.value.pair.PairValue

class MapValue(value: MutableMap<Value<*>, Value<*>>) : Value<MutableMap<Value<*>, Value<*>>>(value) {
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

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<MapValue>() {
        override val name = "Map"

        context(program: Program, arguments: Arguments)
        override fun innerCall(): Value<*> {
            val map = mutableMapOf<Value<*>, Value<*>>()

            for (pair in get<ListValue>("pairs").value) {
                val (first, second) = pair.cast<PairValue>().value
                map[first] = second
            }

            return MapValue(map)
        }

        override fun initializeItems() {
            register(KeysItem)
            register(ValuesItem)
            register(EntriesItem)
        }
    }
}
