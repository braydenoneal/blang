package program.expression

import program.Program
import program.expression.value.Value

class CallExpression(
    val left: Expression,
    val arguments: Arguments,
    var leftValue: Value<*>? = null,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        if (left is DotExpression) {
            if (leftValue == null) {
                leftValue = left.left.evaluate()
            }

            context(arguments) {
                return leftValue!!.callFunction(left.right, left.local)
            }
        }

        if (leftValue == null) {
            leftValue = left.evaluate()
        }

        context(arguments) {
            return leftValue!!.call()
        }
    }

    context(program: Program)
    override fun done() {
        leftValue = null
    }

    override fun toString(): String {
        return "$left($arguments)"
    }
}
