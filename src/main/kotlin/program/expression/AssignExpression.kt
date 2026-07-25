package program.expression

import program.Program
import program.RunException
import program.expression.value.StructValue
import program.expression.value.Value

class AssignExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
    var local: Boolean = false,
) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val value = right.evaluate(program)

        when (left) {
            is IdentifierExpression -> {
                if (operator == "=") {
                    return if (local) {
                        program.scope.setLocal(left.name, value)
                    } else {
                        program.scope.set(left.name, value)
                    }
                }

                val previous = program.scope.get(left.name)
                return program.scope.set(left.name, augmentAssign(program, previous, value))
            }

            is AccessExpression -> {
                val operand = left.left.evaluate(program)
                val item = left.right.evaluate(program)

                if (operator == "=") {
                    return operand.set(item, value)
                }

                val previous = operand.get(item)
                return operand.set(item, augmentAssign(program, previous, value))
            }

            is DotExpression -> {
                val struct = left.left.evaluate(program).cast<StructValue>()

                if (operator == "=") {
                    return struct.setProperty(left.right, value)
                }

                val previous = struct.getProperty(left.right)
                return struct.setProperty(left.right, augmentAssign(program, previous, value))
            }

            else -> throw RunException("Expression is not assignable")
        }
    }

    fun augmentAssign(program: Program, previous: Value<*>, setValue: Value<*>): Value<*> {
        val arithmeticOperator = when (operator) {
            "-=" -> "-"
            "+=" -> "+"
            "//=" -> "//"
            "/=" -> "/"
            "%=" -> "%"
            "*=" -> "*"
            else -> throw RunException("Unrecognized operator")
        }

        return BinaryOperatorExpression(arithmeticOperator, previous, setValue).evaluate(program)
    }
}
