package program.expression

import program.Program
import program.expression.value.Value

class IfElseExpression(
    val condition: Expression,
    val expressionA: Expression,
    val expressionB: Expression,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        val conditionValue = condition.evaluate(program)
        return if (conditionValue.truth()) expressionA.evaluate(program) else expressionB.evaluate(program)
    }
}
