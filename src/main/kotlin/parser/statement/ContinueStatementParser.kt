package parser.statement

import parser.Parser
import program.statement.ContinueStatement
import program.statement.Statement

class ContinueStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expectStatementEnd()
        return ContinueStatement()
    }
}
