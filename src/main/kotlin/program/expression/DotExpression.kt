package program.expression

import parser.expression.BuiltinExpressionParser.valueBuiltins
import program.Program
import program.RunException
import program.expression.value.CallableValue
import program.expression.value.StructValue
import program.expression.value.Value

data class DotExpression(val left: Expression, val right: String) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val value = left.evaluate(program)

        if (value is StructValue && value.has(right)) {
            return value.get(right)
        }

        val type = value.typeString()
        val valueBuiltins = valueBuiltins[value::class]

        if (valueBuiltins != null) {
            val builder = valueBuiltins[right] ?: throw RunException("Type $type does not have builtin $right")
            return CallableValue(builder.invoke(value))
        }

        throw RunException("Struct does not have property $right")
    }
}
