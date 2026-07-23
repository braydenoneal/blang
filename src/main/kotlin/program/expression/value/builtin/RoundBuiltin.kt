package program.expression.value.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.math.roundToInt

class RoundBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.getAny(program, "value")

        if (value is IntegerValue) {
            return value
        } else if (value is FloatValue) {
            return IntegerValue(value.value.roundToInt())
        }

        throw RunException("Expression is not a number")
    }
}
