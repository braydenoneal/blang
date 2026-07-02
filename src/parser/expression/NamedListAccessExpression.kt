package parser.expression

import parser.Program
import parser.RunException
import parser.expression.value.ListValue
import parser.expression.value.Value

data class NamedListAccessExpression(
    val variableExpression: Expression,
    val indices: MutableList<Expression>
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val listValue = variableExpression.evaluate(program) ?: return null

        if (listValue is ListValue) {
            return listValue.get(ListValue.toIndexValues(program, indices) ?: return null)
        }

        throw RunException("Variable is not a list")
    }
}
