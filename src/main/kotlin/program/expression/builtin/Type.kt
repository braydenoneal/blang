package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Callable
import program.expression.value.StringValue
import program.expression.value.Value
import program.expression.value.getAny

object Type : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return StringValue(getAny("value").typeString())
    }
}
