package program.statement

import program.Program
import program.expression.Expression

class ExpressionStatement(val expression: Expression) : Statement {
    override fun innerExecute(program: Program): Statement {
        expression.evaluate(program)
        return this
    }
}
