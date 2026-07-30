package parser.statement

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.statement.DeleteStatement
import program.statement.Statement

class DeleteStatementParser : StatementParser {
    override fun parse(parser: Parser, spanStart: Int): Statement {
        if (!parser.peekIs(Type.IDENTIFIER)) {
            throw ParseException("Expression is not an identifier", parser.spanFrom(spanStart))
        }

        val name = parser.next().value
        parser.expectStatementEnd()
        return DeleteStatement(name)
    }
}
