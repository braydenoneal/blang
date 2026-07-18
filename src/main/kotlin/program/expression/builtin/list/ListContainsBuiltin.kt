package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListContainsBuiltin(
    val listValue: ListValue,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return BooleanValue(listValue.value.contains((arguments.anyValue(program, "value", 0)).evaluate(program)))
    }
}
