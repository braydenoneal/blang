package program.expression.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.builtin.ValueBuiltin
import program.expression.value.StringValue
import program.expression.value.StructValue
import program.expression.value.Value

data class StructRemoveBuiltin(
    override val value: StructValue,
    override val arguments: Arguments,
) : ValueBuiltin<StructValue>(value, arguments) {
    override fun evaluate(program: Program): Value<*> {
        val removeValue = arguments.anyValue(program, "value", 0)

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
