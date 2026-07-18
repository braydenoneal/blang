package program.expression

import program.Program
import program.RunException
import program.expression.value.*

data class BinaryOperatorExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        var a = left.evaluate(program)
        var b = right.evaluate(program)

        if (operator == "==") {
            return BooleanValue(a == b)
        } else if (operator == "!=") {
            return BooleanValue(a != b)
        }

        when {
            a is IntegerValue && b is FloatValue -> a = FloatValue(a.value.toFloat())
            a is FloatValue && b is IntegerValue -> b = FloatValue(b.value.toFloat())
            b is StringValue -> a = StringValue(a.value.toString())
            a is StringValue -> b = StringValue(b.value.toString())
        }

        if (a::class != b::class) {
            throw RunException("Operands are not of the same type")
        }

        val operators = BinaryOperators.binaryOperators[a::class] ?: throw RunException("Type of ${a.typeString()} does not have any binary operators")
        val operatorFunction = operators[operator] ?: throw RunException("Type of ${a.typeString()} does not support binary operator of $operator")

        return operatorFunction.invoke(a, b)
    }
}
