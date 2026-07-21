package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.StringValue
import program.expression.value.Value

data class StringCastBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return StringValue(arguments.getAny(program, "value").value.toString())
    }
}
