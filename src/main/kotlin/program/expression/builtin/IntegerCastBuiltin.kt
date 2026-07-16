package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.Value

data class IntegerCastBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return IntegerValue((arguments.floatValue(program, "value", 0) ?: return null).value.toInt())
    }
}
