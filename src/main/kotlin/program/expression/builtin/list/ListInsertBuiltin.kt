package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.ListValue
import program.expression.value.Value

data class ListInsertBuiltin(
    val listValue: ListValue,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val index = (arguments.integerValue(program, "index", 0)).value
        val insertValue = arguments.anyValue(program, "value", 1)
        listValue.value.add(index, insertValue)
        return listValue
    }
}
