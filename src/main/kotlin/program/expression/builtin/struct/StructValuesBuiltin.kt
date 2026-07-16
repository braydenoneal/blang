package program.expression.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class StructValuesBuiltin(
    val struct: StructValue,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for (entry in struct.value) {
            list.add(entry.second)
        }

        return ListValue(list)
    }
}
