package parser.statement

import parser.Parser
import parser.tokenizer.Type
import program.statement.ImportStatement
import program.statement.Statement

class ImportStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val identifiers: MutableList<String> = mutableListOf()
        parser.expect(Type.IMPORT_KEYWORD)

        while (parser.peekIs(Type.IDENTIFIER)) {
            identifiers.add(parser.next().value)

            if (!parser.peekIs(Type.SEMICOLON) && !parser.peekIsAllowNewline(Type.NEWLINE)) {
                parser.expect(Type.DOT)
            }
        }

        parser.expectStatementEnd()
        return ImportStatement(identifiers)
    }
}
