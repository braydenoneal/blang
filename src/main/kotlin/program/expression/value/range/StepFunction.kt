package program.expression.value.range

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.integer.IntegerValue

object StepFunction : ValueFunction<RangeValue>() {
    override val name: String = "step"

    context(program: Program, arguments: Arguments, value: RangeValue)
    override fun call(): Value<*> {
        val step = get<IntegerValue>("value").value
        return RangeValue(Range(value.value.start, value.value.end, step))
    }
}
