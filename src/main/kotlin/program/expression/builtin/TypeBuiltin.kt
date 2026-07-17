package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.StringValue
import program.expression.value.Value

data class TypeBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = arguments.anyValue(program, "value", 0) ?: return null
        return StringValue(value.typeString())
    }
}
