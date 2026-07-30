package program.expression

import program.Program
import program.expression.value.Value

class CallExpression(
    val left: Expression,
    val arguments: Arguments,
    var leftValue: Value<*>? = null,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        if (leftValue == null) {
            leftValue = left.evaluate(program)
        }

        return leftValue!!.call(program, arguments)
    }

    override fun done(program: Program) {
        leftValue = null
    }
}
