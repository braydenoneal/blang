package program.expression

import program.Program
import program.expression.value.Value

class DotExpression(
    val left: Expression,
    val right: String,
    val local: Boolean,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        return left.evaluate().getItem(right)
    }

    override fun toString(): String {
        return "$left.$right"
    }
}
