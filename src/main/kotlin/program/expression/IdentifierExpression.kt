package program.expression

import program.Program
import program.expression.value.Value
import program.expression.value.identifier.IdentifierValue

class IdentifierExpression(val name: String) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
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
