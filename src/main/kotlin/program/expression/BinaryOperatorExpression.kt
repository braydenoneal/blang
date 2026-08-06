package program.expression

import program.Program
import program.RunException
import program.expression.value.Value
import program.expression.value.booleanvalue.BooleanValue
import program.expression.value.floatvalue.FloatValue
import program.expression.value.integer.IntegerValue
import program.expression.value.string.StringValue

class BinaryOperatorExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        var a = left.evaluate()
        var b = right.evaluate()

        when {
            a is IntegerValue && b is FloatValue -> a = FloatValue(a.value.toFloat())
            a is FloatValue && b is IntegerValue -> b = FloatValue(b.value.toFloat())
            b is StringValue -> a = StringValue(a.value.toString())
            a is StringValue -> b = StringValue(b.value.toString())
        }

        return when (operator) {
            "-" -> a.minus(a.cast(b))
            "+" -> a.plus(a.cast(b))
            "//" -> a.floorDivide(a.cast(b))
            "/" -> a.divide(a.cast(b))
            "%" -> a.remainder(a.cast(b))
            "*" -> a.times(a.cast(b))
            "^" -> a.exponentiate(a.cast(b))
            "<=" -> BooleanValue(a.compareTo(a.cast(b)) <= 0)
            ">=" -> BooleanValue(a.compareTo(a.cast(b)) >= 0)
            "<" -> BooleanValue(a.compareTo(a.cast(b)) < 0)
            ">" -> BooleanValue(a.compareTo(a.cast(b)) > 0)
            "and" -> BooleanValue(a.truth() && b.truth())
            "or" -> BooleanValue(a.truth() || b.truth())
            "==" -> BooleanValue(a.equalsOther(a.cast(b)))
            "!=" -> BooleanValue(!a.equalsOther(a.cast(b)))
            ".." -> a.rangeTo(a.cast(b))
            "in" -> BooleanValue(b.contains(a))
            "!in" -> BooleanValue(!b.contains(a))
            else -> throw RunException("Unknown operator", span)
        }
    }

    override fun toString(): String {
        return "$left $operator $right"
    }
}
