package program.statement

import program.Program

class EmptyStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }
}
