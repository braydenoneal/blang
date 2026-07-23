package parser.statement

import parser.Parser
import parser.tokenizer.Type
import program.statement.ImportStatement
import program.statement.Statement

class ImportStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val identifiers: MutableList<String> = mutableListOf()
        var name: String = parser.expect(Type.IDENTIFIER)
        identifiers.add(name)

        while (parser.peekIs(Type.DOT)) {
            parser.next()
            name = parser.expect(Type.IDENTIFIER)
            identifiers.add(name)
        }

        if (parser.peekIs(Type.AS_KEYWORD)) {
            parser.next()
            name = parser.expect(Type.IDENTIFIER)
        }

        parser.expectStatementEnd()
        return ImportStatement(identifiers, name)
    }
}
