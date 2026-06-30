package parser.expression.operator

import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.Value

data class BooleanOperator(
    val operator: String,
    val operandA: Expression,
    val operandB: Expression
) : Operator, Expression {
    override fun evaluate(program: Program): Value<*> {
        val a = operandA.evaluate(program)
        val b = operandB.evaluate(program)

        if (a is BooleanValue && b is BooleanValue) {
            if (operator == "and") {
                return BooleanValue(a.value && b.value)
            }

            return BooleanValue(a.value || b.value)
        }

        throw RunException("Operands are not booleans")
    }
}
