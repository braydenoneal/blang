package program.expression.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.*
import kotlin.math.abs

object AbsoluteValue : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        val value = getAny("value")

        if (value is IntegerValue) {
            return IntegerValue(abs(value.value))
        } else if (value is FloatValue) {
            return FloatValue(abs(value.value))
        }

        throw RunException("Expression is not a number", arguments.span)
    }
}
