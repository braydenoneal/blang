package parser.expression.builtin.list

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.ListValue
import parser.expression.value.Value


data class ListInsertBuiltin(
    val listValue: ListValue,
    val arguments: Arguments
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val index = arguments.integerValue(program, "index", 0).value
        val insertValue = arguments.anyValue(program, "value", 1)
        listValue.value.add(index, insertValue)
        return listValue
    }
}
