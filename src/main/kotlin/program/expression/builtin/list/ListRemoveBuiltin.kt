package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListRemoveBuiltin(
    override val value: ListValue,
    override val arguments: Arguments,
) : ValueBuiltin<ListValue>(value, arguments) {
    override fun evaluate(program: Program): Value<*> {
        val removeValue = arguments.getAny(program, "value")

        if (removeValue is IntegerValue) {
            value.value.removeAt(removeValue.value)
        } else {
            value.value.remove(removeValue)
        }

        return value
    }
}
