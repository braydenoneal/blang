package parser.expression.operator

import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.FloatValue
import parser.expression.value.IntegerValue
import parser.expression.value.Value

data class ComparisonOperator(
    val operator: String,
    val operandA: Expression,
    val operandB: Expression
) : Operator, Expression {
    override fun evaluate(program: Program): Value<*> {
        val a = operandA.evaluate(program)
        val b = operandB.evaluate(program)

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
