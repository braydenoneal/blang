package program.expression.value.range

import program.Program
import program.expression.value.Value
import program.expression.value.ValueItem
import program.expression.value.integer.IntegerValue

object StartItem : ValueItem<RangeValue>() {
    override val name: String = "start"

    context(program: Program, value: RangeValue)
    override fun get(): Value<*> {
        return IntegerValue(value.value.start)
    }
}
