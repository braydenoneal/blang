package program.expression.value.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.StringValue
import program.expression.value.Value

class TypeBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return StringValue(arguments.getAny(program, "value").typeString())
    }
}
