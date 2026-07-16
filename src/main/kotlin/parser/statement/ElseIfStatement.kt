package parser.statement

import parser.Parser
import parser.expression.Expression
import parser.expression.value.Value
import tokenizer.Type

data class ElseIfStatement(
    val condition: Expression,
    val statements: StatementList,
    var conditionValue: Value<*>?,
) {
    companion object {
        fun parse(parser: Parser): ElseIfStatement {
            val statements = StatementList()

            parser.expect(Type.KEYWORD, "elif")
            val condition = Expression.parse(parser)
            parser.expect(Type.LEFT_CURLY_BRACE)

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)

            return ElseIfStatement(condition, statements, null)
        }
    }
}
