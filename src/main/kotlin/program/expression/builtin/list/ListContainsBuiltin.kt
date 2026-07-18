package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.BooleanValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListContainsBuiltin(
    override val value: ListValue,
    override val arguments: Arguments,
) : ValueBuiltin<ListValue>(value, arguments) {
    override fun evaluate(program: Program): Value<*> {
        return BooleanValue(value.value.contains((arguments.anyValue(program, "value", 0)).evaluate(program)))
    }
}
