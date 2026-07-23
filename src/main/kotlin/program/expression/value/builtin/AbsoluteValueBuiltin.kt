package program.expression.value.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.math.abs

class AbsoluteValueBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.getAny(program, "value")

        if (value is IntegerValue) {
            return IntegerValue(abs(value.value))
        } else if (value is FloatValue) {
            return FloatValue(abs(value.value))
        }

        throw RunException("Expression is not a number")
    }
}
