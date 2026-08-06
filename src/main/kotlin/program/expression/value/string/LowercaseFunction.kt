package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction

object LowercaseFunction : ValueFunction<StringValue>() {
    override val name: String = "lowercase"

    context(program: Program, arguments: Arguments, value: StringValue)
    override fun call(): Value<*> {
        return StringValue(value.value.lowercase())
    }
}
