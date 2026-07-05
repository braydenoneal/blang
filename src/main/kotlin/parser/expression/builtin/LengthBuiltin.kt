package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.IntegerValue
import parser.expression.value.Value


data class LengthBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return IntegerValue((arguments.listValue(program, "value", 0) ?: return null).value.size)
    }
}
