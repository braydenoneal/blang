package program.expression.value.builtin.list

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.BooleanValue
import program.expression.value.ListValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class ListContainsAllBuiltin(override val value: ListValue) : ValueBuiltin<ListValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val nextListValue = arguments.getAny(program, "value")

        if (nextListValue is ListValue) {
            return BooleanValue(value.value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list")
    }
}
