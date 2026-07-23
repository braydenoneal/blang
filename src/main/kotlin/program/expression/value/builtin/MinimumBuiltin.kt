package program.expression.value.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.math.min

class MinimumBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        var a = arguments.getAny(program, "a")
        var b = arguments.getAny(program, "b")

        if (a is IntegerValue && b is FloatValue) {
            a = FloatValue(a.value.toFloat())
        } else if (a is FloatValue && b is IntegerValue) {
            b = FloatValue(b.value.toFloat())
        }

        if (a is IntegerValue && b is IntegerValue) {
            return IntegerValue(min(a.value, b.value))
        } else if (a is FloatValue && b is FloatValue) {
            return FloatValue(min(a.value, b.value))
        }

        throw RunException("Arguments are not numbers")
    }
}
