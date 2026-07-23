package program.expression.value.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.value.BooleanValue
import program.expression.value.ListValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class ListContainsBuiltin(override val value: ListValue) : ValueBuiltin<ListValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return BooleanValue(value.value.contains(arguments.getAny(program, "value").evaluate(program)))
    }
}
