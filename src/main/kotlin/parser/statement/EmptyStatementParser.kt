package parser.statement

import parser.Parser
import program.statement.EmptyStatement
import program.statement.Statement

class EmptyStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        return EmptyStatement()
    }
}
