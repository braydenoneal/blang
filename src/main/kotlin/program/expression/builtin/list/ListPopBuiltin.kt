package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.ListValue
import program.expression.value.Value

data class ListPopBuiltin(
    val listValue: ListValue,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        listValue.value.removeLast()
        return listValue
    }
}
