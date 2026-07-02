package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.Value
import kotlin.math.floor


data class FloorBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return FloatValue(floor((arguments.floatValue(program, "value", 0) ?: return null).value.toDouble()).toFloat())
    }
}
