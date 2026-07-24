package program.statement

import program.Program
import program.expression.value.util.Function

class FunctionStatement(val function: Function) : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
