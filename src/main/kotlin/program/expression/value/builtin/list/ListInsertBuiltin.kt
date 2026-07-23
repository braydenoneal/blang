package program.expression.value.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class ListInsertBuiltin(override val value: ListValue) : ValueBuiltin<ListValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val index = arguments.get<IntegerValue>(program, "index").value
        val insertValue = arguments.getAny(program, "value")
        value.value.add(index, insertValue)
        return value
    }
}
