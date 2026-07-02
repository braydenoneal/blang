package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.*


data class TypeBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = arguments.anyValue(program, "value", 0) ?: return null

        val string = when (value) {
            is BooleanValue -> "boolean"
            is FloatValue -> "float"
            is FunctionValue -> "function"
            is IntegerValue -> "integer"
            is ListValue -> "list"
            is NullValue -> "null"
            is RangeValue -> "range"
            is StringValue -> "string"
            is StructValue -> "struct"
            else -> "null"
        }

        return StringValue(string)
    }
}
