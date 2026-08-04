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
    context(program: Program)
    override fun innerExecute(): Statement {
        if (value == null) {
            val listResult = expression.evaluate()
            value = listResult
        }

        val value = value!!
        val size = value.size()

        if (index >= size) {
            return this
        }

        val item = value.toList()[index]
        program.scope.set(itemName, item)

        val result = statements.runNext()

        if (result is ReturnStatement || result is BreakStatement) {
            return result as? ReturnStatement ?: this
        }

        index++

        if (index >= size) {
            return this
        }

        throw IncompleteException()
    }

    context(program: Program)
    override fun done() {
        value = null
        index = 0
    }

    override fun toString(): String {
        return "for $itemName in $expression { $statements }"
    }
}
