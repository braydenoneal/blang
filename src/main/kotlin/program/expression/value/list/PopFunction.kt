package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction

object PopFunction : ValueFunction<ListValue>() {
    override val name: String = "pop"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        value.value.removeLast()
        return value
    }
}
