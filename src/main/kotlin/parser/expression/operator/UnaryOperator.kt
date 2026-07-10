package parser.expression.operator

import parser.Parser
import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.Value

data class UnaryOperator(val operand: Expression) : Operator, Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = operand.evaluate(program) ?: return null

        if (value is BooleanValue) {
            return BooleanValue(!value.value)
        }

        throw RunException("Operand is not a boolean")
    }

    companion object {
        fun parse(parser: Parser): Expression {
            parser.next()
            return UnaryOperator(Expression.parse(parser))
        }
    }
}
