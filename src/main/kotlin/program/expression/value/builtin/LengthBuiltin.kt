package program.expression.value.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value

class LengthBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return IntegerValue(arguments.get<ListValue>(program, "value").value.size)
    }
}
