package program.statement

import parser.Parser
import parser.tokenizer.Type
import program.Program

class ContinueStatement : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    companion object {
        fun parse(parser: Parser): Statement {
            parser.expect(Type.CONTINUE_KEYWORD)
            parser.expectStatementEnd()
            return ContinueStatement()
        }
    }
}
