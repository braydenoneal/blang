package program.expression

import program.Program
import program.RunException
import program.expression.value.BooleanValue
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

data class UnaryOperatorExpression(
    val operator: String,
    val operand: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = operand.evaluate(program)

        return when (operator) {
            "-" -> when (value) {
                is IntegerValue -> IntegerValue(-value.value)
                is FloatValue -> FloatValue(-value.value)
                else -> throw RunException("Operand is not a number")
            }

            "+" -> when (value) {
                is IntegerValue -> value
                is FloatValue -> value
                else -> throw RunException("Operand is not a number")
            }

            "!" -> when (value) {
                is BooleanValue -> BooleanValue(!value.value)
                else -> throw RunException("Operand is not a boolean")
            }

            else -> throw RunException("Invalid operand")
        }

    }
}
