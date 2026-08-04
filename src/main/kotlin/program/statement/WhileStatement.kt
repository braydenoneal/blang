package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

class WhileStatement(
    val condition: Expression,
    val statements: StatementList,
    var conditionValue: Value<*>? = null,
) : Statement() {
    context(program: Program)
    override fun innerExecute(): Statement {
        if (conditionValue == null) {
            val conditionResult = condition.evaluate()
            conditionValue = conditionResult
        }

        if (conditionValue!!.truth()) {
            val statement = statements.runNext()

            if (statement is ReturnStatement || statement is BreakStatement) {
                return statement
            }

            conditionValue = null
            throw IncompleteException()
        }

        return this
    }

    context(program: Program)
    override fun done() {
        conditionValue = null
    }

    override fun toString(): String {
        return "while $condition { $statements }"
    }
}
