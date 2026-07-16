package program.expression.operator

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.Value

data class BooleanOperator(
    val operator: String,
    val operandA: Expression,
    val operandB: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val a = operandA.evaluate(program) ?: return null
        val b = operandB.evaluate(program) ?: return null

        if (a is BooleanValue && b is BooleanValue) {
            if (operator == "and") {
                return BooleanValue(a.value && b.value)
            }

            return BooleanValue(a.value || b.value)
        }

        throw RunException("Operands are not booleans")
    }
}
