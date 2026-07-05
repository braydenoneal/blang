package parser.expression.builtin.list

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.IntegerValue
import parser.expression.value.ListValue
import parser.expression.value.Value


data class ListRemoveBuiltin(
    val listValue: ListValue,
    val arguments: Arguments
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val removeValue = arguments.anyValue(program, "value", 0)

        if (removeValue is IntegerValue) {
            listValue.value.removeAt(removeValue.value)
        } else {
            listValue.value.remove(removeValue)
        }

        return listValue
    }
}
