package program.expression.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class StructValuesBuiltin(
    override val value: StructValue,
    override val arguments: Arguments,
) : ValueBuiltin<StructValue>(value, arguments) {
    override fun innerEvaluate(program: Program): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for (entry in value.value) {
            list.add(entry.second)
        }

        return ListValue(list)
    }
}
