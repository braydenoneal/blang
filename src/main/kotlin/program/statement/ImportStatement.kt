package program.statement

import parser.Parser
import parser.tokenizer.Type
import program.Program

data class ImportStatement(val identifiers: MutableList<String>) : Statement {
    override fun execute(program: Program): Statement {
        program.addImport(this)
        return this
    }

    companion object {
        fun parse(parser: Parser): Statement {
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
}
