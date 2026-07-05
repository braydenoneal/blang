package parser.expression.builtin.struct

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.StringValue
import parser.expression.value.StructValue
import parser.expression.value.Value


data class StructRemoveBuiltin(
    val struct: StructValue,
    val arguments: Arguments
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
