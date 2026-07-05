package parser.statement

import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.Value
import tokenizer.Type

data class WhileStatement(
    val condition: Expression,
    val statements: StatementList,
    var conditionValue: Value<*>? = null,
) : Statement {
    override fun execute(program: Program): Statement? {
        if (conditionValue == null) {
            val conditionResult = condition.evaluate(program) ?: return null
            conditionValue = conditionResult
        }

        val value = conditionValue

        if (value is BooleanValue) {
            if (value.value) {
                val statement = statements.runNext(program) ?: return null

                if (statement is ReturnStatement || statement is BreakStatement) {
                    conditionValue = null
                    return statement
                }

                conditionValue = null
                return null
            }

            conditionValue = null
            return this
        }

        throw RunException("Expression is not a boolean")
    }

    companion object {
        fun parse(program: Program): Statement {
            val statements = StatementList()

            program.expect(Type.KEYWORD, "while")
            val condition = Expression.parse(program)
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            return WhileStatement(condition, statements)
        }
    }
}
