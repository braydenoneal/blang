package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.Range
import program.expression.value.RangeValue
import program.expression.value.Value

data class RangeBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val start = arguments.get<IntegerValue>(program, "start", IntegerValue(0)).value
        val end = arguments.get<IntegerValue>(program, "end").value
        val step = arguments.get<IntegerValue>(program, "step", IntegerValue(1)).value
        return RangeValue(Range(start, end, step))
    }
}
