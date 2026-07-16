package parser.expression

import parser.Program
import parser.expression.value.ListValue
import parser.expression.value.Value

data class ListExpression(val expressions: MutableList<Expression>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return ListValue(ListValue.toIndexValues(program, expressions) ?: return null)
    }
}
