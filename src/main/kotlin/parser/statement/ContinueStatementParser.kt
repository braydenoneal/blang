package parser.statement

import parser.Parser
import program.statement.ContinueStatement
import program.statement.Statement

class ContinueStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        parser.expectStatementEnd()
        return ContinueStatement()
    }
}
