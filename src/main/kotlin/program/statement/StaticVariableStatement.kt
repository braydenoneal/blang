package program.statement

import program.Program
import program.expression.Expression

class StaticVariableStatement(
    val name: String,
    val expression: Expression,
) : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
