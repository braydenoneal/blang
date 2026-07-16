package program.expression.builtin

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.*

data class TypeBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = arguments.anyValue(program, "value", 0) ?: return null

        return StringValue(
            program.getCustomType(value) ?: when (value) {
                is BooleanValue -> "boolean"
                is FloatValue -> "float"
                is FunctionValue -> "function"
                is IntegerValue -> "integer"
                is ListValue -> "list"
                is NullValue -> "null"
                is RangeValue -> "range"
                is StringValue -> "string"
                is StructValue -> "struct"
                else -> throw RunException("Unrecognized value type")
            },
        )
    }
}
