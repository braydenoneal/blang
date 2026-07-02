package parser.statement

import parser.Program
import parser.expression.Expression
import tokenizer.Type

data class ExpressionStatement(val expression: Expression) : Statement {
    override fun execute(program: Program): Statement? {
        expression.evaluate(program) ?: return null
        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            val expression = Expression.parse(program)
            program.expect(Type.SEMICOLON)
            return ExpressionStatement(expression)
        }
    }
}
