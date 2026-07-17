package parser.statement

import parser.Parser
import parser.Parsers.expressionStatementParser
import parser.Parsers.statementParsers
import program.statement.Statement

interface StatementParser {
    fun parse(parser: Parser): Statement

    companion object {
        fun parse(parser: Parser): Statement {
            val token = parser.peekAllowNewline()
            val statementParser = statementParsers[token.type] ?: expressionStatementParser
            return statementParser.parse(parser)
        }
    }
}
