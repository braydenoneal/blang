package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.value.Null
import program.statement.ReturnStatement
import program.statement.Statement

class ReturnStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expect(Type.RETURN_KEYWORD)
        val expression = if (parser.peekIs(Type.SEMICOLON) || parser.peekIsAllowNewline(Type.NEWLINE) || parser.peekIsAllowNewline(Type.RIGHT_CURLY_BRACE)) Null.VALUE else ExpressionParser.parse(parser)
        parser.expectStatementEnd()
        return ReturnStatement(expression)
    }
}
