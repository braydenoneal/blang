package program.expression.builtin.list

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value

data class ListRemoveBuiltin(
    val listValue: ListValue,
    val arguments: Arguments,
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
