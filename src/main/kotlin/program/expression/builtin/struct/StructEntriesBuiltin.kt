package program.expression.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.ListValue
import program.expression.value.StringValue
import program.expression.value.StructValue
import program.expression.value.Value

data class StructEntriesBuiltin(
    override val value: StructValue,
    override val arguments: Arguments,
) : ValueBuiltin<StructValue>(value, arguments) {
    override fun evaluate(program: Program): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for ((first, second) in value.value) {
            list.add(StructValue(mutableListOf("key" to StringValue(first), "value" to second)))
        }

        return ListValue(list)
    }
}
