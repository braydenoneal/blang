package program.expression.builtin.list

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.BooleanValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListContainsAllBuiltin(
    override val value: ListValue,
    override val arguments: Arguments,
) : ValueBuiltin<ListValue>(value, arguments) {
    override fun evaluate(program: Program): Value<*> {
        val nextListValue = arguments.getAny(program, "value")

        if (nextListValue is ListValue) {
            return BooleanValue(value.value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list")
    }
}
