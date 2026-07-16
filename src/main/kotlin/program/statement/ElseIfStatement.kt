package program.statement

import parser.Parser
import parser.statement.StatementParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.Value

data class ElseIfStatement(
    val condition: Expression,
    val statements: StatementList,
    var conditionValue: Value<*>?,
) {
    companion object {
        fun parse(parser: Parser): ElseIfStatement {
            val statements = StatementList()

            parser.expect(Type.ELIF_KEYWORD)
            val condition = Expression.parse(parser)
            parser.expect(Type.LEFT_CURLY_BRACE)

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(StatementParser.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)

            return ElseIfStatement(condition, statements, null)
        }
    }
}
