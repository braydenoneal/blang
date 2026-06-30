package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.FloatValue
import parser.expression.value.Value


data class FloatCastBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return FloatValue(arguments.integerValue(program, "value", 0).value.toFloat())
    }
}
