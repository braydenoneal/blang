package program.expression.value.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.FloatValue
import program.expression.value.Value
import kotlin.math.floor

class FloorBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return FloatValue(floor(arguments.get<FloatValue>(program, "value").value.toDouble()).toFloat())
    }
}
