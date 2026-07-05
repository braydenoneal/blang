package parser.expression.builtin.list

import parser.Program
import parser.RunException
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.ListValue
import parser.expression.value.Value


data class ListContainsAllBuiltin(
    val listValue: ListValue,
    val arguments: Arguments
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val nextListValue = arguments.anyValue(program, "value", 0) ?: return null

        if (nextListValue is ListValue) {
            return BooleanValue(listValue.value.containsAll(nextListValue.value))
        }

        throw RunException("Expression is not a list")
    }
}
