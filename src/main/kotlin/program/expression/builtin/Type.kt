package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.getAny
import program.expression.value.string.StringValue

object Type : Builtin() {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return StringValue(getAny("value").static.name)
    }
}
