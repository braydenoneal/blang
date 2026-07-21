package program.statement

import program.Program

class BreakStatement : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
