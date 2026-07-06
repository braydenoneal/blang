package parser.statement

import parser.Program
import parser.expression.Expression
import parser.expression.value.Value
import tokenizer.Type

data class ElseIfStatement(
    val condition: Expression,
    val statements: StatementList,
    var conditionValue: Value<*>?,
) {
    companion object {
        fun parse(program: Program): ElseIfStatement {
            val statements = StatementList()

            program.expect(Type.KEYWORD, "elif")
            val condition = Expression.parse(program)
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            return ElseIfStatement(condition, statements, null)
        }
    }
}
