package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.integer.IntegerValue

object SubstringFunction : ValueFunction<StringValue>() {
    override val name: String = "substring"

    context(program: Program, arguments: Arguments, value: StringValue)
    override fun call(): Value<*> {
        val start = get<IntegerValue>("start").value
        val end = get<IntegerValue>("end").value
        return StringValue(value.value.substring(start, end))
    }
}
