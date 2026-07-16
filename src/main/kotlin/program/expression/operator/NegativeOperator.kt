package program.expression.operator

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

data class NegativeOperator(val operand: Expression) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = operand.evaluate(program) ?: return null

        return when (value) {
            is IntegerValue -> IntegerValue(-value.value)
            is FloatValue -> FloatValue(-value.value)
            else -> throw RunException("Operand is not a number")
        }
    }
}
