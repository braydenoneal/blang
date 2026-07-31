package program.expression

import program.Program
import program.expression.value.IdentifierValue
import program.expression.value.Value

class IdentifierExpression(val name: String) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        val variable = program.scope.getNullable(name)

        if (variable != null) {
            return variable
        }

        return IdentifierValue(name).withSpan(span)
    }

    override fun toString(): String {
        return name
    }
}
