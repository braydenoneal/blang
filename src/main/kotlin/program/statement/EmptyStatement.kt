package program.statement

import program.Program

class EmptyStatement : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
