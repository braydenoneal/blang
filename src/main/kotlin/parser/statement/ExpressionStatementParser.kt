package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import program.statement.ExpressionStatement
import program.statement.Statement

class ExpressionStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val expression = ExpressionParser.parse(parser)
        parser.expectStatementEnd()
        return ExpressionStatement(expression)
    }
}
