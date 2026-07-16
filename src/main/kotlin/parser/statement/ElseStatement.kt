package parser.statement

import parser.Parser
import tokenizer.Type

data class ElseStatement(val statements: StatementList) {
    companion object {
        fun parse(parser: Parser): ElseStatement {
            val statements = StatementList()

            parser.expect(Type.KEYWORD, "else")
            parser.expect(Type.LEFT_CURLY_BRACE)

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)

            return ElseStatement(statements)
        }
    }
}
