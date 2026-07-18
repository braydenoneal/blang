package program.expression

import program.Program
import program.RunException
import program.expression.operator.ArithmeticOperator
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class AssignmentExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = right.evaluate(program) ?: return null

        when (left) {
            is IdentifierExpression -> {
                if (operator == "=") {
                    return program.scope.set(left.name, value)
                }

                val prev = program.scope.get(left.name)
                val arithmetic = ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program) ?: return null

                return program.scope.set(left.name, arithmetic)
            }

            is ListAccessExpression -> {
                val list = left.listExpression.evaluate(program)

                if (list !is ListValue) {
                    throw RunException("Expression is not a list")
                }

                val index = left.indexExpression.evaluate(program) ?: return null

                if (operator == "=") {
                    return list.set(index, value)
                }

                val prev = list.get(index)
                val arithmetic = ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program) ?: return null

                return list.set(index, arithmetic)
            }

            is DotExpression -> {
                val struct = left.left.evaluate(program) ?: return null

                if (struct !is StructValue) {
                    throw RunException("Expression is not a struct")
                }

                if (operator == "=") {
                    return struct.set(left.right, value)
                }

                val prev = struct.get(left.right)
                val arithmetic = ArithmeticOperator(if (operator == "+=") "+" else "-", prev, value).evaluate(program) ?: return null

                return struct.set(left.right, arithmetic)
            }

            else -> throw RunException("Expression is not assignable")
        }
    }
}
