package program.statement

import program.Program

data class StatementList(
    val ran: MutableList<Statement> = mutableListOf(),
    val toRun: MutableList<Statement> = mutableListOf(),
) {
    fun runNext(program: Program): Statement? {
        if (toRun.isEmpty()) {
            return EmptyStatement()
        }

        val result = toRun.first().execute(program) ?: return null

        ran.add(toRun.removeFirst())

        if (result is ReturnStatement || result is BreakStatement || result is ContinueStatement) {
            toRun.addAll(ran)
            ran.clear()

            return result
        }

        if (toRun.isEmpty()) {
            toRun.addAll(ran)
            ran.clear()

            return toRun.last()
        }

        return null
    }

    fun add(statement: Statement) {
        toRun.add(statement)
    }

    fun clear() {
        ran.clear()
        toRun.clear()
    }
}
