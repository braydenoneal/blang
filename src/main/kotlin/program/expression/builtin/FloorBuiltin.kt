package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.FloatValue
import program.expression.value.Value
import kotlin.math.floor

data class FloorBuiltin(override val arguments: Arguments) : Builtin(arguments) {
    override fun innerEvaluate(program: Program): Value<*> {
        return FloatValue(floor(arguments.get<FloatValue>(program, "value").value.toDouble()).toFloat())
    }
}
