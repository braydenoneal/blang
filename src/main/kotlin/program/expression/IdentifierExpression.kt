package program.expression

import program.Program
import program.expression.value.Value

data class IdentifierExpression(val name: String) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return program.scope.get(name)
    }
}
