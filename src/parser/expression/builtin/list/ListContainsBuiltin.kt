package parser.expression.builtin.list

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.ListValue
import parser.expression.value.Value


data class ListContainsBuiltin(
    val listValue: ListValue,
    val arguments: Arguments
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return BooleanValue(listValue.value.contains(arguments.anyValue(program, "value", 0).evaluate(program)))
    }
}
