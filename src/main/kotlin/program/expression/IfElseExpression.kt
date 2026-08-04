package program.expression

import program.Program
import program.expression.value.Value

class IfElseExpression(
    val condition: Expression,
    val expressionA: Expression,
    val expressionB: Expression,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        val conditionValue = condition.evaluate()
        return if (conditionValue.truth()) expressionA.evaluate() else expressionB.evaluate()
    }

    override fun toString(): String {
        return "$expressionA if $condition else $expressionB"
    }
}
