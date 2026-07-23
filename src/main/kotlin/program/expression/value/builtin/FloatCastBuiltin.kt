package program.expression.value.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value

class FloatCastBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return FloatValue(arguments.get<IntegerValue>(program, "value").value.toFloat())
    }
}
