package program.expression

import program.Program
import program.expression.value.Value
import program.expression.value.function.Function
import program.expression.value.function.FunctionValue

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
