package program.statement

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.Value

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
}
