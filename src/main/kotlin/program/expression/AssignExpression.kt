package program.expression

import program.Program
import program.RunException
import program.expression.value.Value

class AssignExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
    var local: Boolean = false,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        val value = right.evaluate()

        when (left) {
            is IdentifierExpression -> {
                if (operator == "=") {
                    return if (local) {
                        program.scope.setLocal(left.name, value)
                    } else {
                        program.scope.set(left.name, value)
                    }
                }

                val previous = program.scope.get(left.name, span)
                return program.scope.set(left.name, augmentAssign(previous, value))
            }

            is AccessExpression -> {
                val operand = left.left.evaluate()
                val item = left.right.evaluate()

                if (operator == "=") {
                    return operand.set(item, value)
                }

                val previous = operand.get(item)
                return operand.set(item, augmentAssign(previous, value))
            }

            is DotExpression -> {
                val leftValue = left.left.evaluate()

                if (operator == "=") {
                    return leftValue.assignItem(left.right, value)
                }

                val previous = leftValue.getItem(left.right)
                return leftValue.assignItem(left.right, augmentAssign(previous, value))
            }

            else -> throw RunException("Expression is not assignable", span)
        }
    }

    context(program: Program)
    fun augmentAssign(previous: Value<*>, setValue: Value<*>): Value<*> {
        val arithmeticOperator = when (operator) {
            "-=" -> "-"
            "+=" -> "+"
            "//=" -> "//"
            "/=" -> "/"
            "%=" -> "%"
            "*=" -> "*"
            else -> throw RunException("Unrecognized operator", span)
        }

        return BinaryOperatorExpression(arithmeticOperator, previous, setValue).evaluate()
    }

    override fun toString(): String {
        return "${if (local) "var " else ""}$left $operator $right"
    }
}
