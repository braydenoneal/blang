package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

class WhileStatement(
    val condition: Expression,
    val statements: StatementList,
    var conditionValue: Value<*>? = null,
) : Statement() {
    override fun innerExecute(program: Program): Statement {
        if (conditionValue == null) {
            val conditionResult = condition.evaluate(program)
            conditionValue = conditionResult
        }

        if (conditionValue!!.truth()) {
            val statement = statements.runNext(program)

            if (statement is ReturnStatement || statement is BreakStatement) {
                return statement
            }

            conditionValue = null
            throw IncompleteException()
        }

        return this
    }

    override fun done(program: Program) {
        conditionValue = null
    }
}
