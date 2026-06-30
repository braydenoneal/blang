package parser.expression.builtin.list

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.ListValue
import parser.expression.value.Value


data class ListAppendBuiltin(
    val listValue: ListValue,
    val arguments: Arguments
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val appendValue = arguments.anyValue(program, "value", 0)
        listValue.value.add(appendValue)
        return listValue
    }
}
