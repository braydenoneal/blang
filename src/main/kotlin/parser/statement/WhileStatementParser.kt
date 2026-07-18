package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.statement.Statement
import program.statement.StatementList
import program.statement.WhileStatement

class WhileStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val statements = StatementList()
        val condition = ExpressionParser.parse(parser)
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            statements.add(StatementParser.parse(parser))
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)

        return WhileStatement(condition, statements)
    }
}
