package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.StringValue
import program.expression.value.Value

data class StringCastBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return StringValue((arguments.anyValue(program, "value", 0) ?: return null).value.toString())
    }
}
