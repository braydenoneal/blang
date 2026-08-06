package program.expression.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.floatvalue.FloatValue
import program.expression.value.getAny
import program.expression.value.integer.IntegerValue
import kotlin.math.max

object Maximum : Builtin() {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        var a = getAny("a")
        var b = getAny("b")

        if (a is IntegerValue && b is FloatValue) {
            a = FloatValue(a.value.toFloat())
        } else if (a is FloatValue && b is IntegerValue) {
            b = FloatValue(b.value.toFloat())
        }

        if (a is IntegerValue && b is IntegerValue) {
            return IntegerValue(max(a.value, b.value))
        } else if (a is FloatValue && b is FloatValue) {
            return FloatValue(max(a.value, b.value))
        }

        throw RunException("Arguments are not numbers", arguments.span)
    }
}
