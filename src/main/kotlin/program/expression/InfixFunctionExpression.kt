package program.expression

import program.Program
import program.expression.value.Value

class InfixFunctionExpression(
    val name: String,
    val left: Expression,
    val arguments: Arguments,
    var leftValue: Value<*>? = null,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        if (leftValue == null) {
            leftValue = left.evaluate()
        }

        context(arguments) {
            return leftValue!!.withSpan(span).callFunction(name)
        }
    }

    context(program: Program)
    override fun done() {
        leftValue = null
    }

    override fun toString(): String {
        return "$left $name $arguments"
    }
}
