package parser.statement

import parser.Program

class EmptyStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }
}
