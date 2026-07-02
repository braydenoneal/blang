package parser.statement

import parser.Program
import tokenizer.Type

data class ElseStatement(val statements: StatementList) {
    companion object {
        fun parse(program: Program): ElseStatement {
            val statements = StatementList()

            program.expect(Type.KEYWORD, "else")
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            return ElseStatement(statements)
        }
    }
}
