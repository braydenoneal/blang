package parser.statement

import parser.Program
import parser.expression.Expression
import parser.expression.value.IntegerValue
import parser.expression.value.ListValue
import parser.expression.value.RangeValue
import tokenizer.Type

data class ForStatement(
    val itemName: String,
    val listExpression: Expression,
    val statements: MutableList<Statement>
) : Statement {
    override fun execute(program: Program): Statement {
        val value = listExpression.evaluate(program)

        if (value is ListValue) {
            for (item in value.value) {
                program.scope.set(itemName, item)
                val statement = Statement.runStatements(program, statements)

                if (statement is ReturnStatement) {
                    return statement
                } else if (statement is BreakStatement) {
                    break
                }
            }
        } else if (value is RangeValue) {
            for (i in value.value.start..<value.value.end step value.value.step) {
                program.scope.set(itemName, IntegerValue(i))
                val statement = Statement.runStatements(program, statements)

                if (statement is ReturnStatement) {
                    return statement
                } else if (statement is BreakStatement) {
                    break
                }
            }
        }

        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            val statements: MutableList<Statement> = ArrayList()

            program.expect(Type.KEYWORD, "for")
            val itemName = program.expect(Type.IDENTIFIER)
            program.expect(Type.KEYWORD, "in")
            val expression = Expression.parse(program)
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")
            return ForStatement(itemName, expression, statements)
        }
    }
}
