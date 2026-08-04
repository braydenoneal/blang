package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Callable
import program.expression.value.StringValue
import program.expression.value.Value
import program.expression.value.util.Null

object Print : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        val value = arguments.getAny("value", StringValue(""))
        var string = value.toString()

        if (value is StringValue) {
            string = string.substring(1, string.length - 1)
        }

        println(string)
        return Null.VALUE
    }
}
