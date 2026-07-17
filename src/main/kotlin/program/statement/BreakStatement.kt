package program.statement

import program.Program

class BreakStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }
}
