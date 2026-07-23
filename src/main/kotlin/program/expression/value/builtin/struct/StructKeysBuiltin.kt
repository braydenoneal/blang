package program.expression.value.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.value.ListValue
import program.expression.value.StringValue
import program.expression.value.StructValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class StructKeysBuiltin(override val value: StructValue) : ValueBuiltin<StructValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val list: MutableList<Value<*>> = mutableListOf()

        for (entry in value.value) {
            list.add(StringValue(entry.first))
        }

        return ListValue(list)
    }
}
