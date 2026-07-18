package program.expression

import program.Program
import program.RunException
import program.expression.value.Value

data class UnaryOperatorExpression(
    val operator: String,
    val operand: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = operand.evaluate(program)

        val operators = UnaryOperators.unaryOperators[value::class] ?: throw RunException("Type of ${value.typeString()} does not have any unary operators")
        val operatorFunction = operators[operator] ?: throw RunException("Type of ${value.typeString()} does not support unary operator of $operator")

        return operatorFunction.invoke(value)
    }
}
