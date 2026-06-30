package parser.statement

import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.BooleanValue
import tokenizer.Type

data class WhileStatement(val condition: Expression, val statements: MutableList<Statement>) : Statement {
    override fun execute(program: Program): Statement {
        val start = System.currentTimeMillis()
        var value = condition.evaluate(program)

        while (value is BooleanValue && value.value) {
            val statement: Statement? = Statement.runStatements(program, statements)

            if (statement is ReturnStatement) {
                return statement
            } else if (statement is BreakStatement) {
                break
            }

            if (System.currentTimeMillis() - start > 20) {
                throw RunException("Maximum while statement iterations exceeded")
            }

            value = condition.evaluate(program)
        }

        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            val statements: MutableList<Statement> = ArrayList()

            program.expect(Type.KEYWORD, "while")
            val condition = Expression.parse(program)
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            return WhileStatement(condition, statements)
        }
    }
}
