package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListInsertBuiltin(
    override val value: ListValue,
    override val arguments: Arguments,
) : ValueBuiltin<ListValue>(value, arguments) {
    override fun evaluate(program: Program): Value<*> {
        val index = arguments.get<IntegerValue>(program, "index").value
        val insertValue = arguments.getAny(program, "value")
        value.value.add(index, insertValue)
        return value
    }
}
