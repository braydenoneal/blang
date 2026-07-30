package program.statement

import program.Program

class StatementList(
    val statements: MutableList<Statement> = mutableListOf(),
    var index: Int = 0,
) {
    fun runNext(program: Program): Statement {
        if (statements.isEmpty()) {
            return EmptyStatement()
        }

        val result = statements[index].execute(program)
        index++

        if (result is ReturnStatement || result is BreakStatement || result is ContinueStatement) {
            index = 0
            return result
        }

        if (index >= statements.size) {
            index = 0
            return statements.last()
        }

        throw IncompleteException()
    }

    fun add(statement: Statement) {
        statements.add(statement)
    }

    fun clear() {
        statements.clear()
    }
}
