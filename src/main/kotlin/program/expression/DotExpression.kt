package program.expression

import program.Program
import program.expression.value.Value

class DotExpression(val left: Expression, val right: String) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        return left.evaluate(program).getIdentifier(program, right)
    }

    override fun toString(): String {
        return "$left.$right"
    }
}
