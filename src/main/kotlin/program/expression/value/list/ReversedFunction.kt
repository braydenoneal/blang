package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction

object ReversedFunction : ValueFunction<ListValue>() {
    override val name: String = "reversed"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        return ListValue(value.value.reversed().toMutableList())
    }
}
