package program.expression

import program.Program
import program.expression.value.Value

class SliceExpression(
    val left: Expression,
    val from: Expression,
    val to: Expression,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        return left.evaluate(program).slice(from.evaluate(program), to.evaluate(program))
    }

    override fun toString(): String {
        return "$left[$from:$to]"
    }
}
