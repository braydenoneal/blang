package program.expression

import program.Program
import program.expression.value.FunctionValue
import program.expression.value.Value
import program.expression.value.util.Function

class GetExpression(val function: Function) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        context(Arguments(mutableListOf(), mutableMapOf())) {
            return FunctionValue(function).call()
        }
    }

    override fun toString(): String {
        return "get: ${function.statements}"
    }
}
