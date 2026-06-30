package parser.statement

import parser.Program
import parser.expression.Expression
import parser.expression.value.BooleanValue
import tokenizer.Type

data class IfStatement(
    val condition: Expression,
    val statements: MutableList<Statement>,
    val elseIfStatements: MutableList<ElseIfStatement>,
    val elseStatement: ElseStatement?
) : Statement {
    override fun execute(program: Program): Statement? {
        val value = condition.evaluate(program)

        if (value is BooleanValue && value.value) {
            return Statement.runStatements(program, statements)
        }

        for (elseIfStatement in elseIfStatements) {
            val elseIfValue = elseIfStatement.condition.evaluate(program)

            if (elseIfValue is BooleanValue && elseIfValue.value) {
                return Statement.runStatements(program, elseIfStatement.statements)
            }
        }

        if (elseStatement == null) {
            return this
        }

        return Statement.runStatements(program, elseStatement.statements)
    }

    companion object {
        fun parse(program: Program): Statement {
            val statements: MutableList<Statement> = ArrayList()
            val elseIfStatements: MutableList<ElseIfStatement> = ArrayList()
            var elseStatement: ElseStatement? = null

            program.expect(Type.KEYWORD, "if")
            val condition = Expression.parse(program)
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            while (program.peekIs(Type.KEYWORD, "elif")) {
                elseIfStatements.add(ElseIfStatement.parse(program))
            }

            if (program.peekIs(Type.KEYWORD, "else")) {
                elseStatement = ElseStatement.parse(program)
            }

            return IfStatement(condition, statements, elseIfStatements, elseStatement)
        }
    }
}

