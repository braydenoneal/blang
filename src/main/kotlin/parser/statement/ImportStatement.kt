package parser.statement

import parser.Parser
import parser.Program
import tokenizer.Type

data class ImportStatement(val identifiers: MutableList<String>) : Statement {
    override fun execute(program: Program): Statement {
        program.addImport(this)
        return this
    }

    companion object {
        fun parse(parser: Parser): Statement {
            val identifiers: MutableList<String> = mutableListOf()
            parser.expect(Type.KEYWORD, "import")

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
