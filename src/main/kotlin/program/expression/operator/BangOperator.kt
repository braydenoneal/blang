package program.expression.operator

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.Value

data class BangOperator(val operand: Expression) : Operator, Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = operand.evaluate(program) ?: return null

        if (value is BooleanValue) {
            return BooleanValue(!value.value)
        }

        throw RunException("Operand is not a boolean")
    }
}
