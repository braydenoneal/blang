package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.value.nullvalue.Null
import program.statement.ReturnStatement
import program.statement.Statement

class ReturnStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val expression = when (parser.peekAllowNewline().type) {
            Type.SEMICOLON, Type.NEWLINE, Type.RIGHT_CURLY_BRACE -> Null.VALUE
            else -> ExpressionParser.parse()
        }
        parser.expectStatementEnd()
        return ReturnStatement(expression)
    }
}
