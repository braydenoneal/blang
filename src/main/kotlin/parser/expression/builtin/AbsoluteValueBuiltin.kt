package parser.expression.builtin

import parser.Program
import parser.RunException
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.IntegerValue
import parser.expression.value.Value
import kotlin.math.abs


data class AbsoluteValueBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = arguments.anyValue(program, "value", 0) ?: return null

        if (value is IntegerValue) {
            return IntegerValue(abs(value.value))
        } else if (value is FloatValue) {
            return FloatValue(abs(value.value))
        }

        throw RunException("Expression is not a number")
    }
}
