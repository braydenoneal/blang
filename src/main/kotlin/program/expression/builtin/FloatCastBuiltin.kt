package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.Value

data class FloatCastBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return FloatValue((arguments.integerValue(program, "value", 0) ?: return null).value.toFloat())
    }
}
