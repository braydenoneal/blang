package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.floatvalue.FloatValue
import program.expression.value.get
import kotlin.math.floor

object Floor : Builtin() {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return FloatValue(floor(get<FloatValue>("value").value.toDouble()).toFloat())
    }
}
