package parser.statement

import parser.Parser
import parser.tokenizer.Type
import program.statement.BreakStatement
import program.statement.Statement

class BreakStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expect(Type.BREAK_KEYWORD)
        parser.expectStatementEnd()
        return BreakStatement()
    }
}
