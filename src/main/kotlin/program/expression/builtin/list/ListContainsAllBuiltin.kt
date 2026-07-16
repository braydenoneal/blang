package program.expression.builtin.list

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListContainsAllBuiltin(
    val listValue: ListValue,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val nextListValue = arguments.anyValue(program, "value", 0) ?: return null

        if (nextListValue is ListValue) {
            return BooleanValue(listValue.value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list")
    }
}
