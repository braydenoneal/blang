package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.booleanvalue.BooleanValue

object ContainsFunction : ValueFunction<ListValue>() {
    override val name: String = "contains"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        return BooleanValue(value.value.contains(getAny("value")))
    }
}
