package program.expression.value.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.value.StringValue
import program.expression.value.StructValue
import program.expression.value.Value
import program.expression.value.builtin.ValueBuiltin

class StructRemoveBuiltin(override val value: StructValue) : ValueBuiltin<StructValue>(value) {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val removeValue = arguments.getAny(program, "value")

        if (removeValue is StringValue) {
            for (i in value.value.indices) {
                if (value.value[i].first == removeValue.value) {
                    value.value.removeAt(i)
                }
            }
        }

        return value
    }
}
