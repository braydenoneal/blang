package program.expression.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.*
import kotlin.math.roundToInt

object Round : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        val value = getAny("value")

        if (value is IntegerValue) {
            return value
        } else if (value is FloatValue) {
            return IntegerValue(value.value.roundToInt())
        }

        throw RunException("Expression is not a number", arguments.span)
    }
}
