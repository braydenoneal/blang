package parser.expression.operator

import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.IntegerValue
import parser.expression.value.Value

data class NegativeOperator(val operand: Expression) : Operator, Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = operand.evaluate(program) ?: return null

        return when (value) {
            is IntegerValue -> IntegerValue(-value.value)
            is FloatValue -> FloatValue(-value.value)
            else -> throw RunException("Operand is not a number")
        }
    }
}
