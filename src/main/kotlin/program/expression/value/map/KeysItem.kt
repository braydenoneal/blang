package program.expression.value.map

import program.Program
import program.expression.value.Value
import program.expression.value.ValueItem
import program.expression.value.list.ListValue

object KeysItem : ValueItem<MapValue>() {
    override val name: String = "keys"

    context(program: Program, value: MapValue)
    override fun get(): Value<*> {
        return ListValue(value.value.keys.toMutableList())
    }
}
