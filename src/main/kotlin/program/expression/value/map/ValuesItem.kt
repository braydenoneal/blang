package program.expression.value.map

import program.Program
import program.expression.value.Value
import program.expression.value.ValueItem
import program.expression.value.list.ListValue

object ValuesItem : ValueItem<MapValue>() {
    override val name: String = "values"

    context(program: Program, value: MapValue)
    override fun get(): Value<*> {
        return ListValue(value.value.values.toMutableList())
    }
}
