package program.expression.value.list

import program.Program
import program.expression.value.Value
import program.expression.value.ValueItem
import program.expression.value.integer.IntegerValue

object SizeItem : ValueItem<ListValue>() {
    override val name: String = "size"

    context(program: Program, value: ListValue)
    override fun get(): Value<*> {
        return IntegerValue(value.value.size)
    }
}
