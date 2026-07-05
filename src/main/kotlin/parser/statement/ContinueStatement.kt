package parser.statement

import parser.Program
import tokenizer.Type

class ContinueStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            program.expect(Type.KEYWORD, "continue")
            program.expect(Type.SEMICOLON)
            return ContinueStatement()
        }
    }
}
