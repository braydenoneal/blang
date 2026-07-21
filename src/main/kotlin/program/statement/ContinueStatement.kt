package program.statement

import program.Program

class ContinueStatement : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
