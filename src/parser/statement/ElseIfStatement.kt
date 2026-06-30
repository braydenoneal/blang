package parser.statement

import parser.Program
import parser.expression.Expression
import tokenizer.Type

data class ElseIfStatement(val condition: Expression, val statements: MutableList<Statement>) {
    companion object {
        fun parse(program: Program): ElseIfStatement {
            val statements: MutableList<Statement> = ArrayList()

            program.expect(Type.KEYWORD, "elif")
            val condition = Expression.parse(program)
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            return ElseIfStatement(condition, statements)
        }
    }
}
