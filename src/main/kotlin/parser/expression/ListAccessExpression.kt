package parser.expression

import parser.Program
import parser.RunException
import parser.expression.value.ListValue
import parser.expression.value.Value

data class ListAccessExpression(val listExpression: Expression, val indices: MutableList<Expression>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val listValue = listExpression.evaluate(program) ?: return null

        if (listValue is ListValue) {
            return listValue.get(ListValue.toIndexValues(program, indices) ?: return null)
        }

        throw RunException("Expression is not a list")
    }
}
