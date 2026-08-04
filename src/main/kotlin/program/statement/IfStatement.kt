package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

class IfStatement(
    val condition: Expression,
    val statements: StatementList,
    val elseIfStatements: MutableList<ElseIfStatement>,
    val elseStatement: ElseStatement?,
    var conditionValue: Value<*>?,
) : Statement() {
    context(program: Program)
    override fun innerExecute(): Statement {
        if (conditionValue == null) {
            val conditionResult = condition.evaluate()
            conditionValue = conditionResult
        }

        if (conditionValue!!.truth()) {
            val result = statements.runNext()
            return result
        }

        for (elseIfStatement in elseIfStatements) {
            if (elseIfStatement.conditionValue == null) {
                val elseIfStatementConditionResult = elseIfStatement.condition.evaluate()
                elseIfStatement.conditionValue = elseIfStatementConditionResult
            }

            val elseIfValue = elseIfStatement.conditionValue

            if (elseIfValue!!.truth()) {
                val result = elseIfStatement.statements.runNext()
                return result
            }
        }

        if (elseStatement == null) {
            return this
        }

        val result = elseStatement.statements.runNext()
        return result
    }

    context(program: Program)
    override fun done() {
        conditionValue = null

        for (elseIfStatement in elseIfStatements) {
            elseIfStatement.conditionValue = null
        }
    }

    override fun toString(): String {
        val string = StringBuilder("if $condition { $statements }")

        for (statement in elseIfStatements) {
            string.append(" elif ${statement.condition} { ${statement.statements} }")
        }

        if (elseStatement != null) {
            string.append(" else { ${elseStatement.statements} }")
        }

        return string.toString()
    }

    class ElseIfStatement(
        val condition: Expression,
        val statements: StatementList,
        var conditionValue: Value<*>?,
    )

    class ElseStatement(val statements: StatementList)
}
