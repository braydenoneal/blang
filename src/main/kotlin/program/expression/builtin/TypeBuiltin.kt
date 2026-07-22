package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.StringValue
import program.expression.value.Value

data class TypeBuiltin(override val arguments: Arguments) : Builtin(arguments) {
    override fun innerEvaluate(program: Program): Value<*> {
        return StringValue(arguments.getAny(program, "value").typeString())
    }
}
