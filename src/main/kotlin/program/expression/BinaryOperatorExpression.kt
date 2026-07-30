package program.expression

import program.Program
import program.RunException
import program.expression.value.*

class BinaryOperatorExpression(
    val operator: String,
    val left: Expression,
    val right: Expression,
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        var a = left.evaluate(program)
        var b = right.evaluate(program)

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
}
