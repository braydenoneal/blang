package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.Range
import program.expression.value.RangeValue
import program.expression.value.Value

data class RangeBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val end = if (arguments.namelessArguments.size == 1) (arguments.integerValue(program, "end", 0) ?: return null).value else (arguments.integerValue(program, "end", 1) ?: return null).value
        val start = if (arguments.namelessArguments.size > 1) (arguments.integerValue(program, "start", 0) ?: return null).value else 0
        val step = if (arguments.namelessArguments.size > 2) (arguments.integerValue(program, "step", 2) ?: return null).value else 1

        return RangeValue(Range(start, end, step))
    }
}
