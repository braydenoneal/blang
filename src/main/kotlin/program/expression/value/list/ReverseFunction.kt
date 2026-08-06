package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction

object ReverseFunction : ValueFunction<ListValue>() {
    override val name: String = "reverse"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        value.value.reverse()
        return value
    }
}
