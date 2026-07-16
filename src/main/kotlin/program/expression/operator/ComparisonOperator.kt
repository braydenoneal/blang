package program.expression.operator

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

data class ComparisonOperator(
    val operator: String,
    val operandA: Expression,
    val operandB: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val a = operandA.evaluate(program) ?: return null
        val b = operandB.evaluate(program) ?: return null

        if (operator == "==") {
            return BooleanValue(a == b)
        } else if (operator == "!=") {
            return BooleanValue(a != b)
        }

        if (a is IntegerValue && b is IntegerValue) {
            return when (operator) {
                "<=" -> BooleanValue(a.value <= b.value)
                ">=" -> BooleanValue(a.value >= b.value)
                "<" -> BooleanValue(a.value < b.value)
                else -> BooleanValue(a.value > b.value)
            }
        } else if (a is FloatValue && b is FloatValue) {
            return when (operator) {
                "<=" -> BooleanValue(a.value <= b.value)
                ">=" -> BooleanValue(a.value >= b.value)
                "<" -> BooleanValue(a.value < b.value)
                else -> BooleanValue(a.value > b.value)
            }
        }

        throw RunException("Operands are not comparable")
    }
}
