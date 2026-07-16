package program.expression

import program.Program
import program.RunException
import program.expression.value.StructValue
import program.expression.value.Value

data class MemberExpression(val member: Expression, val property: String) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = member.evaluate(program) ?: return null

        if (value is StructValue) {
            return value.get(property)
        }

        throw RunException("Expression is not a struct")
    }
}
