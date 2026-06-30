package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.Value
import kotlin.math.ceil


data class CeilBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return FloatValue(ceil(arguments.floatValue(program, "value", 0).value.toDouble()).toFloat())
    }
}
