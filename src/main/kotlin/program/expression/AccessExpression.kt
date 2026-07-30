package program.expression

import program.Program
import program.expression.value.Value

class AccessExpression(
    val left: Expression,
    val right: Expression,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        return left.evaluate(program).get(right.evaluate(program))
    }

    override fun toString(): String {
        return "$left[$right]"
    }
}
