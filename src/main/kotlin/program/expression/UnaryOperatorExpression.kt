package program.expression

import program.Program
import program.RunException
import program.expression.value.BooleanValue
import program.expression.value.Value

class UnaryOperatorExpression(
    val operator: String,
    val operand: Expression,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        val value = operand.evaluate(program)

        return when (operator) {
            "-" -> value.negative()
            "+" -> value.positive()
            "!" -> BooleanValue(!value.truth())
            else -> throw RunException("Unknown operator", span)
        }
    }
}
