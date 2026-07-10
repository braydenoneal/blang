package parser.statement

import parser.Parser
import tokenizer.Type

data class ElseStatement(val statements: StatementList) {
    companion object {
        fun parse(parser: Parser): ElseStatement {
            val statements = StatementList()

            parser.expect(Type.KEYWORD, "else")
            parser.expect(Type.CURLY_BRACE, "{")

            while (!parser.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.CURLY_BRACE, "}")

            return ElseStatement(statements)
        }
    }
}
