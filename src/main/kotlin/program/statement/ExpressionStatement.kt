package program.statement

import program.Program
import program.expression.Expression

data class ExpressionStatement(val expression: Expression) : Statement {
    override fun execute(program: Program): Statement {
        expression.evaluate(program)
        return this
    }
}
