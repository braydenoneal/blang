package program.expression

import program.Program
import program.expression.value.CallableValue
import program.expression.value.Value

data class CallExpression(
    val left: Expression,
    val arguments: Arguments,
    var leftValue: CallableValue? = null,
) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        if (leftValue == null) {
            leftValue = left.evaluate(program).cast<CallableValue>()
        }

        return leftValue!!.call(program, arguments)
    }

    override fun done(program: Program) {
        leftValue = null
    }
}
