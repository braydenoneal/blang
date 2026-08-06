package program.expression.value.pair

import program.Program
import program.expression.value.Value
import program.expression.value.ValueItem

object FirstItem : ValueItem<PairValue>() {
    override val name: String = "first"

    context(program: Program, value: PairValue)
    override fun get(): Value<*> {
        return value.value.first
    }
}
