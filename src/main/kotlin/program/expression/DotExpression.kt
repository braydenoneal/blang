package program.expression

import program.Program
import program.expression.value.Value

class DotExpression(
    val left: Expression,
    val right: String,
    val local: Boolean,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        return left.evaluate(program).getItem(program, right)
    }

    override fun toString(): String {
        return "$left.$right"
    }
}
