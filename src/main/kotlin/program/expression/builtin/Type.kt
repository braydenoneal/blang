package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Callable
import program.expression.value.StringValue
import program.expression.value.Value

object Type : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return StringValue(arguments.getAny("value").typeString())
    }
}
