package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.FunctionValue

class StaticStatements(
    val functions: MutableMap<String, FunctionValue>,
    val variables: MutableMap<String, Expression>,
) : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
