package parser.statement

import parser.Parser
import parser.tokenizer.Type
import program.statement.ContinueStatement
import program.statement.Statement

class ContinueStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expect(Type.CONTINUE_KEYWORD)
        parser.expectStatementEnd()
        return ContinueStatement()
    }
}
