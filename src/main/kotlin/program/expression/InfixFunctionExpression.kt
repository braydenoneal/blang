package program.expression

import program.Program
import program.expression.value.FunctionReferenceValue
import program.expression.value.Value
import program.expression.value.util.FunctionReference

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

        return FunctionReferenceValue(FunctionReference(leftValue, name)).withSpan(span).call(program, arguments)
    }

    override fun done(program: Program) {
        leftValue = null
    }

    override fun toString(): String {
        return "$left $name $arguments"
    }
}
