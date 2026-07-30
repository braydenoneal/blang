package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

class ForStatement(
    val itemName: String,
    val expression: Expression,
    val statements: StatementList,
    var value: Value<*>? = null,
    var index: Int = 0,
) : Statement() {
    override fun innerExecute(program: Program): Statement {
        if (value == null) {
            val listResult = expression.evaluate(program)
            value = listResult
        }

        val value = value!!
        val size = value.iteratorSize()

        if (index >= size) {
            return this
        }

        val item = value.iteratorGet(index)
        program.scope.set(itemName, item)

        val result = statements.runNext(program)

        if (result is ReturnStatement || result is BreakStatement) {
            return result as? ReturnStatement ?: this
        }

        index++

        if (index >= size) {
            return this
        }

        throw IncompleteException()
    }

    override fun done(program: Program) {
        value = null
        index = 0
    }
}
