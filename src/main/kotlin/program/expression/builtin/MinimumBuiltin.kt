package program.expression.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.math.min

data class MinimumBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        var a = arguments.anyValue(program, "a", 0) ?: return null
        var b = arguments.anyValue(program, "b", 1) ?: return null

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
