package program.expression

import program.Program
import program.expression.value.Value

data class VariableExpression(val name: String) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return program.scope.get(name)
    }
}
