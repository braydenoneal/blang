package parser.expression.builtin.struct

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.ListValue
import parser.expression.value.StructValue
import parser.expression.value.Value


data class StructValuesBuiltin(
    val struct: StructValue,
    val arguments: Arguments
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val list: MutableList<Value<*>> = ArrayList()

        for (entry in struct.value) {
            list.add(entry.second)
        }

        return ListValue(list)
    }
}
