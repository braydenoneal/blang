package parser.statement

import parser.Parser
import program.statement.BreakStatement
import program.statement.Statement

class BreakStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        parser.expectStatementEnd()
        return BreakStatement()
    }
}
