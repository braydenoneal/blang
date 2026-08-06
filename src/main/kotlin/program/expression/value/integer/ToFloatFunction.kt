package program.expression.value.integer

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.floatvalue.FloatValue

object ToFloatFunction : ValueFunction<IntegerValue>() {
    override val name: String = "toFloat"

    context(program: Program, arguments: Arguments, value: IntegerValue)
    override fun call(): Value<*> {
        return FloatValue(value.value.toFloat())
    }
}
