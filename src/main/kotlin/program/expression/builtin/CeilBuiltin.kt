package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.Value
import kotlin.math.ceil

data class CeilBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return FloatValue(ceil((arguments.floatValue(program, "value", 0) ?: return null).value.toDouble()).toFloat())
    }
}
