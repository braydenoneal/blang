package program.expression.value.list

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.booleanvalue.BooleanValue

object ContainsAllFunction : ValueFunction<ListValue>() {
    override val name: String = "containsAll"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        val nextListValue = getAny("value")

        if (nextListValue is ListValue) {
            return BooleanValue(value.value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list", value.span)
    }
}
