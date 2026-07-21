package program.expression

import program.Program
import program.RunException
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class AssignExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val value = right.evaluate(program)

        when (left) {
            is IdentifierExpression -> {
                if (operator == "=") {
                    return program.scope.set(left.name, value)
                }

                val prev = program.scope.get(left.name)
                val arithmetic = BinaryOperatorExpression(if (operator == "+=") "+" else "-", prev, value).evaluate(program)

                return program.scope.set(left.name, arithmetic)
            }

            is AccessExpression -> {
                val list = left.left.evaluate(program).cast<ListValue>()
                val index = left.right.evaluate(program)

                if (operator == "=") {
                    return list.set(index, value)
                }

                val prev = list.get(index)
                val arithmetic = BinaryOperatorExpression(if (operator == "+=") "+" else "-", prev, value).evaluate(program)

                return list.set(index, arithmetic)
            }

            is DotExpression -> {
                val struct = left.left.evaluate(program).cast<StructValue>()

                if (operator == "=") {
                    return struct.set(left.right, value)
                }

                val prev = struct.get(left.right)
                val arithmetic = BinaryOperatorExpression(if (operator == "+=") "+" else "-", prev, value).evaluate(program)

                return struct.set(left.right, arithmetic)
            }

            else -> throw RunException("Expression is not assignable")
        }
    }
}
