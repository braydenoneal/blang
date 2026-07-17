package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.Value

data class IfStatement(
    val condition: Expression,
    val statements: StatementList,
    val elseIfStatements: MutableList<ElseIfStatement>,
    val elseStatement: ElseStatement?,
    var conditionValue: Value<*>?,
) : Statement {
    override fun execute(program: Program): Statement? {
        if (conditionValue == null) {
            val conditionResult = condition.evaluate(program) ?: return null
            conditionValue = conditionResult
        }

        val value = conditionValue

        if (value is BooleanValue && value.value) {
            val result = statements.runNext(program) ?: return null
            reset()
            return result
        }

        for (elseIfStatement in elseIfStatements) {
            if (elseIfStatement.conditionValue == null) {
                val elseIfStatementConditionResult = elseIfStatement.condition.evaluate(program) ?: return null
                elseIfStatement.conditionValue = elseIfStatementConditionResult
            }

            val elseIfValue = elseIfStatement.conditionValue

            if (elseIfValue is BooleanValue && elseIfValue.value) {
                val result = elseIfStatement.statements.runNext(program) ?: return null
                reset()
                return result
            }
        }

        if (elseStatement == null) {
            reset()
            return this
        }

        val result = elseStatement.statements.runNext(program) ?: return null
        reset()
        return result
    }

    fun reset() {
        conditionValue = null

        for (elseIfStatement in elseIfStatements) {
            elseIfStatement.conditionValue = null
        }
    }

    class ElseIfStatement(
        val condition: Expression,
        val statements: StatementList,
        var conditionValue: Value<*>?,
    )

    class ElseStatement(val statements: StatementList)
}
