package program.expression.value.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Null
import program.expression.value.StringValue
import program.expression.value.Value

class PrintBuiltin : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.getAny(program, "value", StringValue(""))
        var string = value.toString()

        if (value is StringValue) {
            string = string.substring(1, string.length - 1)
        }

        println(string)
        return Null.VALUE
    }
}
