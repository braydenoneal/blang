package program.statement

import parser.Parser
import parser.statement.StatementParser
import parser.tokenizer.Type

data class ElseStatement(val statements: StatementList) {
    companion object {
        fun parse(parser: Parser): ElseStatement {
            val statements = StatementList()

            parser.expect(Type.ELSE_KEYWORD)
            parser.expect(Type.LEFT_CURLY_BRACE)

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(StatementParser.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)

            return ElseStatement(statements)
        }
    }
}
