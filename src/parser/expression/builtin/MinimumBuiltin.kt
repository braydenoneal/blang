package parser.expression.builtin

import parser.Program
import parser.RunException
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.IntegerValue
import parser.expression.value.Value
import kotlin.math.min


data class MinimumBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*> {
        var a = arguments.anyValue(program, "a", 0)
        var b = arguments.anyValue(program, "b", 1)

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
