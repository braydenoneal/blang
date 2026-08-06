package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction

object UppercaseFunction : ValueFunction<StringValue>() {
    override val name: String = "uppercase"

    context(program: Program, arguments: Arguments, value: StringValue)
    override fun call(): Value<*> {
        return StringValue(value.value.uppercase())
    }
}
