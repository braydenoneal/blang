package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import program.statement.ExpressionStatement
import program.statement.Statement

class ExpressionStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val expression = ExpressionParser.parse()
        parser.expectStatementEnd()
        return ExpressionStatement(expression)
    }
}
