package program.statement

import program.Program

class ContinueStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }
}
