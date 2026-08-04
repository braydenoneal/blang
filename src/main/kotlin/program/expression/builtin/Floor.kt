package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Callable
import program.expression.value.FloatValue
import program.expression.value.Value
import kotlin.math.floor

object Floor : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return FloatValue(floor(arguments.get<FloatValue>("value").value.toDouble()).toFloat())
    }
}
