package program.expression

import program.Program
import program.expression.value.FunctionValue
import program.expression.value.Value
import program.expression.value.util.Function

class GetExpression(val function: Function) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return FunctionValue(function).call(program, Arguments.EMPTY)
    }
}
