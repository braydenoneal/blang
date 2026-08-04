package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.statement.Statement
import program.statement.StatementList
import program.statement.WhileStatement

class WhileStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val statements = StatementList()
        val condition = ExpressionParser.parse()
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            statements.add(StatementParser.parse())
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)

        return WhileStatement(condition, statements)
    }
}
