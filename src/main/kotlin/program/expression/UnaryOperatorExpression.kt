package program.expression

import program.Program
import program.RunException
import program.expression.value.Value
import program.expression.value.booleanvalue.BooleanValue

class UnaryOperatorExpression(
    val operator: String,
    val operand: Expression,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        val value = operand.evaluate()

        return when (operator) {
            "-" -> value.negative()
            "+" -> value.positive()
            "!" -> BooleanValue(!value.truth())
            else -> throw RunException("Unknown operator", span)
        }
    }

    override fun toString(): String {
        return "$operator$operand"
    }
}
