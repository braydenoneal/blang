package parser.statement

import parser.Program
import tokenizer.Type

class BreakStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            program.expect(Type.KEYWORD, "break")
            program.expect(Type.SEMICOLON)
            return BreakStatement()
        }
    }
}
