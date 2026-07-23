package program.expression.value.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class ListRemoveBuiltin(override val value: ListValue) : ValueBuiltin<ListValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val removeValue = arguments.getAny(program, "value")

        if (removeValue is IntegerValue) {
            value.value.removeAt(removeValue.value)
        } else {
            value.value.remove(removeValue)
        }

        return value
    }
}
