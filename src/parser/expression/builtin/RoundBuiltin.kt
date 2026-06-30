package parser.expression.builtin

import parser.Program
import parser.RunException
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.IntegerValue
import parser.expression.value.Value
import kotlin.math.roundToInt


data class RoundBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = arguments.anyValue(program, "value", 0)

        if (value is IntegerValue) {
            return value
        } else if (value is FloatValue) {
            return IntegerValue(value.value.roundToInt())
        }

        throw RunException("Expression is not a number")
    }
}
