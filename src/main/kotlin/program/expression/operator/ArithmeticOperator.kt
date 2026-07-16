package program.expression.operator

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.*
import java.util.stream.Stream
import kotlin.math.floor
import kotlin.math.pow

data class ArithmeticOperator(
    val operator: String,
    val operandA: Expression,
    val operandB: Expression,
) : Operator, Expression {
    override fun evaluate(program: Program): Value<*>? {
        var a = operandA.evaluate(program) ?: return null
        var b = operandB.evaluate(program) ?: return null

        if (a is IntegerValue && b is FloatValue) {
            a = FloatValue(a.value.toFloat())
        } else if (a is FloatValue && b is IntegerValue) {
            b = FloatValue(b.value.toFloat())
        } else if (b is StringValue) {
            a = StringValue(a.value.toString())
        } else if (a is StringValue) {
            b = StringValue(b.value.toString())
        }

        if (a is IntegerValue && b is IntegerValue) {
            return when (operator) {
                "+" -> IntegerValue(a.value + b.value)
                "-" -> IntegerValue(a.value - b.value)
                "*" -> IntegerValue(a.value * b.value)
                "//", "/" -> IntegerValue(a.value / b.value)
                "%" -> IntegerValue((a.value + b.value) % b.value)
                else -> IntegerValue(a.value.toDouble().pow(b.value.toDouble()).toInt())
            }
        } else if (a is FloatValue && b is FloatValue) {
            return when (operator) {
                "+" -> FloatValue(a.value + b.value)
                "-" -> FloatValue(a.value - b.value)
                "*" -> FloatValue(a.value * b.value)
                "//" -> FloatValue(floor((a.value / b.value).toDouble()).toFloat())
                "/" -> FloatValue(a.value / b.value)
                "%" -> FloatValue((a.value + b.value) % b.value)
                else -> FloatValue(a.value.toDouble().pow(b.value.toDouble()).toFloat())
            }
        } else if (operator == "+" && a is StringValue && b is StringValue) {
            return StringValue(a.value + b.value)
        } else if (operator == "+" && a is ListValue && b is ListValue) {
            return ListValue(Stream.concat(a.value.stream(), b.value.stream()).toList())
        }

        throw RunException("Invalid operands")
    }
}
