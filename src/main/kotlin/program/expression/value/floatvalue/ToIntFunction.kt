package program.expression.value.floatvalue

import program.Program
import program.expression.Arguments
import program.expression.value.integer.IntegerValue
import program.expression.value.Value
import program.expression.value.ValueFunction

object ToIntFunction : ValueFunction<FloatValue>() {
    override val name: String = "toInt"

    context(program: Program, arguments: Arguments, value: FloatValue)
    override fun call(): Value<*> {
        return IntegerValue(value.value.toInt())
    }
}
