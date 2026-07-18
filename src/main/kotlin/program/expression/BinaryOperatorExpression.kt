package program.expression

import program.Program
import program.RunException
import program.expression.value.*
import kotlin.math.floor
import kotlin.math.pow

data class BinaryOperatorExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        var a = left.evaluate(program)
        var b = right.evaluate(program)

        if (operator == "==") {
            return BooleanValue(a == b)
        } else if (operator == "!=") {
            return BooleanValue(a != b)
        }

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
                "<=" -> BooleanValue(a.value <= b.value)
                ">=" -> BooleanValue(a.value >= b.value)
                "<" -> BooleanValue(a.value < b.value)
                ">" -> BooleanValue(a.value > b.value)
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
                "<=" -> BooleanValue(a.value <= b.value)
                ">=" -> BooleanValue(a.value >= b.value)
                "<" -> BooleanValue(a.value < b.value)
                ">" -> BooleanValue(a.value > b.value)
                else -> FloatValue(a.value.toDouble().pow(b.value.toDouble()).toFloat())
            }
        } else if (operator == "+" && a is StringValue && b is StringValue) {
            return StringValue(a.value + b.value)
        } else if (operator == "+" && a is ListValue && b is ListValue) {
            return ListValue(a.value.plus(b.value).toMutableList())
        } else if (a is BooleanValue && b is BooleanValue) {
            if (operator == "and") {
                return BooleanValue(a.value && b.value)
            }

            return BooleanValue(a.value || b.value)
        }

        throw RunException("Invalid operands")
    }
}
