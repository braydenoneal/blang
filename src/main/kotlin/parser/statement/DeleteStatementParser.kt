package parser.statement

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.statement.DeleteStatement
import program.statement.Statement

class DeleteStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expect(Type.DEL_KEYWORD)

        if (parser.peek().type != Type.IDENTIFIER) {
            throw ParseException("Expression is not an identifier")
        }

        val name = parser.next().value
        parser.expectStatementEnd()
        return DeleteStatement(name)
    }
}
