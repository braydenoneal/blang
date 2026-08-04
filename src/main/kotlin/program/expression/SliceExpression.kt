package program.expression

import program.Program
import program.expression.value.Value

class SliceExpression(
    val left: Expression,
    val from: Expression?,
    val to: Expression?,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        return left.evaluate().slice(from?.evaluate(), to?.evaluate())
    }

    override fun toString(): String {
        return "$left[$from:$to]"
    }
}
