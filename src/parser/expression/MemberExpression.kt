package parser.expression

import parser.Program
import parser.RunException
import parser.expression.value.StructValue
import parser.expression.value.Value

data class MemberExpression(val member: Expression, val property: String) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = member.evaluate(program)

        if (value is StructValue) {
            return value.get(property)
        }

        throw RunException("Expression is not a struct")
    }
}
