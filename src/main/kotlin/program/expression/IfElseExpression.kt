package program.expression

import program.Program
import program.RunException
import program.expression.value.BooleanValue
import program.expression.value.Value

data class IfElseExpression(
    val condition: Expression,
    val expressionA: Expression,
    val expressionB: Expression,
) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val conditionValue = condition.evaluate(program)

        if (conditionValue is BooleanValue) {
            return if (conditionValue.value) expressionA.evaluate(program) else expressionB.evaluate(program)
        }

        throw RunException("Condition is not a boolean")
    }
}
