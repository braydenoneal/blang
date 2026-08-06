package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.integer.IntegerValue

object LengthFunction : ValueFunction<StringValue>() {
    override val name: String = "length"

    context(program: Program, arguments: Arguments, value: StringValue)
    override fun call(): Value<*> {
        return IntegerValue(value.value.length)
    }
}
