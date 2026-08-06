package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.integer.IntegerValue

object RemoveFunction : ValueFunction<ListValue>() {
    override val name: String = "remove"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        val removeValue = getAny("value")

        if (removeValue is IntegerValue) {
            value.value.removeAt(removeValue.value)
        } else {
            value.value.remove(removeValue)
        }

        return value
    }
}
