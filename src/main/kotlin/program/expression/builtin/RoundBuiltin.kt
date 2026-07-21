package program.expression.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.math.roundToInt

data class RoundBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val value = arguments.getAny(program, "value")

        if (value is IntegerValue) {
            return value
        } else if (value is FloatValue) {
            return IntegerValue(value.value.roundToInt())
        }

        throw RunException("Expression is not a number")
    }
}
