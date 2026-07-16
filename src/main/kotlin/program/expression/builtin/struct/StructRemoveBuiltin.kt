package program.expression.builtin.struct

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.StringValue
import program.expression.value.StructValue
import program.expression.value.Value

data class StructRemoveBuiltin(
    val struct: StructValue,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val removeValue = arguments.anyValue(program, "value", 0) ?: return null

        if (removeValue is StringValue) {
            for (i in struct.value.indices) {
                if (struct.value[i].first == removeValue.value) {
                    struct.value.removeAt(i)
                }
            }
        }

        return struct
    }
}
