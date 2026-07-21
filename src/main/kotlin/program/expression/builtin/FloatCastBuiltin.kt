package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

data class FloatCastBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return FloatValue(arguments.get<IntegerValue>(program, "value").value.toFloat())
    }
}
