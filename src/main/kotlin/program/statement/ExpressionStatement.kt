package program.statement

import program.Program
import program.expression.Expression

class ExpressionStatement(val expression: Expression) : Statement() {
    context(program: Program)
    override fun innerExecute(): Statement {
        expression.evaluate()
        return this
    }

    override fun toString(): String {
        return expression.toString()
    }
}
