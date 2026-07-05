package parser.statement

import parser.Program
import tokenizer.Type

data class ImportStatement(val identifiers: MutableList<String>) : Statement {
    override fun execute(program: Program): Statement {
        program.addImport(this)
        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            val identifiers: MutableList<String> = ArrayList()
            program.expect(Type.KEYWORD, "import")

            while (program.peekIs(Type.IDENTIFIER)) {
                identifiers.add(program.next().value)

                if (program.peek().type != Type.SEMICOLON) {
                    program.expect(Type.DOT)
                }
            }

            program.expect(Type.SEMICOLON)
            return ImportStatement(identifiers)
        }
    }
}
