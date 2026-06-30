package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.StringValue
import parser.expression.value.Value


data class StringCastBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return StringValue(arguments.anyValue(program, "value", 0).value.toString())
    }
}
