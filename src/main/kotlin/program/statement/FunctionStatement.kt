package program.statement

import program.Program
import program.expression.Arguments
import program.expression.value.Function
import program.expression.value.Value

data class FunctionStatement(val name: String, val function: Function) : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    fun call(program: Program, arguments: Arguments): Value<*> {
        return function.call(program, arguments)
    }
}
