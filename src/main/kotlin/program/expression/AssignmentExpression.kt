package program.expression

import program.Program
import program.RunException
import program.expression.operator.ArithmeticOperator
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class AssignmentExpression(
    val operator: String,
    val variableExpression: Expression,
    val expression: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = expression.evaluate(program) ?: return null

        if (variableExpression is VariableExpression) {
            if (operator == "=") {
                return program.scope.set(variableExpression.name, value)
            }

            val prev = program.scope.get(variableExpression.name)
            val arithmetic = ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program) ?: return null
            return program.scope.set(variableExpression.name, arithmetic)
        } else if (variableExpression is ListAccessExpression && variableExpression.listExpression is VariableExpression) {
            val listValue = variableExpression.listExpression.evaluate(program)

            if (listValue is ListValue) {
                val indexValues = ListValue.toIndexValues(program, variableExpression.indices) ?: return null

                if (operator == "=") {
                    return listValue.set(indexValues, value)
                }

                val prev = listValue.get(indexValues)
                val arithmetic = ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program) ?: return null
                return listValue.set(indexValues, arithmetic)
            }
        } else if (variableExpression is MemberExpression) {
            val struct = variableExpression.member.evaluate(program) ?: return null

            if (struct is StructValue) {
                if (operator == "=") {
                    return struct.set(variableExpression.property, value)
                }

                val prev = struct.get(variableExpression.property)
                val arithmetic = ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program) ?: return null
                struct.set(variableExpression.property, arithmetic)
            }

        }

        throw RunException("Expression is not assignable")
    }
}
