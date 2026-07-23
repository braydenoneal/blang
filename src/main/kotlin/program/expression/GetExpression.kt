package program.expression

import program.Program
import program.expression.value.Function
import program.expression.value.Value

data class GetExpression(val function: Function) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return function.call(program, Arguments.EMPTY)
    }
}
