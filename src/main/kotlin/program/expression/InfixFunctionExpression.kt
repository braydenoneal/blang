package program.expression

import program.Program
import program.expression.value.Value

class InfixFunctionExpression(
    val name: String,
    val left: Expression,
    val arguments: Arguments,
    var leftValue: Value<*>? = null,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        if (leftValue == null) {
            leftValue = left.evaluate(program)
        }

        return leftValue!!.withSpan(span).callFunction(program, arguments, name)
    }

    override fun done(program: Program) {
        leftValue = null
    }

    override fun toString(): String {
        return "$left $name $arguments"
    }
}
