package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.statement.ForStatement
import program.statement.Statement
import program.statement.StatementList

class ForStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val statements = StatementList()
        val itemName = parser.expect(Type.IDENTIFIER)
        parser.expect(Type.IN_KEYWORD)
        val expression = ExpressionParser.parse(parser)
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!(parser.peekIs(Type.RIGHT_CURLY_BRACE))) {
            statements.add(StatementParser.parse(parser))
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        return ForStatement(itemName, expression, statements)
    }
}
