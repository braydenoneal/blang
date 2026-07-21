package program.statement

import program.Program

interface Statement {
    fun execute(program: Program): Statement {
        try {
            val statement = innerExecute(program)
            done(program)
            return statement
        } catch (_: IncompleteException) {
            abort(program)
            throw IncompleteException()
        }
    }

    fun innerExecute(program: Program): Statement

    fun abort(program: Program) {}

    fun done(program: Program) {}
}
