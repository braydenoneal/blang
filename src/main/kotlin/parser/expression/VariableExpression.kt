package parser.expression

import parser.Program
import parser.expression.value.Value

data class VariableExpression(val name: String) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return program.scope.get(name)
    }
}
