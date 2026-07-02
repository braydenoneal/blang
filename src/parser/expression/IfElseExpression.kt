package parser.expression

import parser.Program
import parser.RunException
import parser.expression.value.BooleanValue
import parser.expression.value.Value
import tokenizer.Type

data class IfElseExpression(
    val condition: Expression,
    val expressionA: Expression,
    val expressionB: Expression
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val conditionValue = condition.evaluate(program) ?: return null

        if (conditionValue is BooleanValue) {
            return if (conditionValue.value) expressionA.evaluate(program) ?: return null else expressionB.evaluate(program) ?: return null
        }

        throw RunException("Condition is not a boolean")
    }

    companion object {
        fun parse(program: Program, expressionA: Expression): Expression {
            program.expect(Type.KEYWORD, "if")
            val condition: Expression = Expression.parse(program)
            program.expect(Type.KEYWORD, "else")
            val expressionB: Expression = Expression.parse(program)
            return IfElseExpression(condition, expressionA, expressionB)
        }
    }
}
