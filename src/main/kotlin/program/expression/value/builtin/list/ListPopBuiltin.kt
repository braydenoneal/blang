package program.expression.value.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.value.ListValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class ListPopBuiltin(override val value: ListValue) : ValueBuiltin<ListValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        value.value.removeLast()
        return value
    }
}
