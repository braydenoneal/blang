package program.expression

import program.Program
import program.RunException
import program.expression.value.FunctionValue
import program.expression.value.Value

data class CallExpression(val name: String, val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return call(program, program)
    }

    fun call(program: Program, functionProgram: Program): Value<*>? {
        val function = functionProgram.getFunction(name)

        if (function != null) {
            return function.call(program, arguments)
        }

        val variable = program.scope.get(name)

        if (variable is FunctionValue) {
            return variable.call(program, arguments)
        }

        throw RunException("'$name' does not refer to a function")
    }
}
