package program.expression.operator

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

data class PositiveOperator(val operand: Expression) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = operand.evaluate(program)

        return when (value) {
            is IntegerValue -> value
            is FloatValue -> value
            else -> throw RunException("Operand is not a number")
        }
    }
}
