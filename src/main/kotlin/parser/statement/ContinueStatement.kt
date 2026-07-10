package parser.statement

import parser.Parser
import parser.Program
import tokenizer.Type

class ContinueStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    companion object {
        fun parse(parser: Parser): Statement {
            parser.expect(Type.KEYWORD, "continue")
            parser.expect(Type.SEMICOLON)
            return ContinueStatement()
        }
    }
}
