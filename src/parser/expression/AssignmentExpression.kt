package parser.expression

import parser.Program
import parser.RunException
import parser.expression.operator.ArithmeticOperator
import parser.expression.value.ListValue
import parser.expression.value.StructValue
import parser.expression.value.Value
import tokenizer.Type

data class AssignmentExpression(
    val operator: String,
    val variableExpression: Expression,
    val expression: Expression
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = expression.evaluate(program)

        if (variableExpression is VariableExpression) {
            if (operator == "=") {
                return program.scope.set(variableExpression.name, value)
            }

            val prev = program.scope.get(variableExpression.name) ?: throw RunException("Variable '" + variableExpression.name + "' does not exist")
            return program.scope.set(variableExpression.name, ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program))
        } else if (variableExpression is NamedListAccessExpression) {
            val listValue: Value<*> = variableExpression.variableExpression.evaluate(program)

            if (listValue is ListValue) {
                val indexValues = ListValue.toIndexValues(program, variableExpression.indices)

                if (operator == "=") {
                    return listValue.set(indexValues, value)
                }

                val prev = listValue.get(indexValues)
                return listValue.set(indexValues, ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program))
            }
        } else if (variableExpression is MemberExpression) {
            val struct = variableExpression.member.evaluate(program)

            if (struct is StructValue) {
                if (operator == "=") {
                    return struct.set(variableExpression.property, value)
                }

                val prev = struct.get(variableExpression.property)
                struct.set(variableExpression.property, ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program))
            }

        }

        throw RunException("Expression is not assignable")
    }

    companion object {
        fun parse(program: Program, variableExpression: Expression): Expression {
            val type = program.expect(Type.ASSIGN)
            val expression = Expression.parse(program)
            return AssignmentExpression(type, variableExpression, expression)
        }
    }
}
