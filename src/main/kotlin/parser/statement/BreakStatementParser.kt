package parser.statement

import parser.Parser
import program.statement.BreakStatement
import program.statement.Statement

class BreakStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expectStatementEnd()
        return BreakStatement()
    }
}
