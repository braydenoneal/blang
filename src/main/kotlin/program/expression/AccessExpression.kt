package program.expression

import program.Program
import program.expression.value.Value

class AccessExpression(
    val left: Expression,
    val right: Expression,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        return left.evaluate().get(right.evaluate())
    }

    override fun toString(): String {
        return "$left[$right]"
    }
}
