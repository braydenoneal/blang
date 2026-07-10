package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.Range
import parser.expression.value.RangeValue
import parser.expression.value.Value

data class RangeBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val end = if (arguments.namelessArguments.size == 1) (arguments.integerValue(program, "end", 0) ?: return null).value else (arguments.integerValue(program, "end", 1) ?: return null).value
        val start = if (arguments.namelessArguments.size > 1) (arguments.integerValue(program, "start", 0) ?: return null).value else 0
        val step = if (arguments.namelessArguments.size > 2) (arguments.integerValue(program, "step", 2) ?: return null).value else 1

        return RangeValue(Range(start, end, step))
    }
}
