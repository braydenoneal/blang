package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

data class IntegerCastBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return IntegerValue(arguments.get<FloatValue>(program, "value").value.toInt())
    }
}
