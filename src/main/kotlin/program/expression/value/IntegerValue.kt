package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Range
import kotlin.math.pow

class IntegerValue(value: Int) : Value<Int>(value) {
    override fun typeString(): String = "integer"

    override fun negative(): Value<*> {
        return IntegerValue(-value)
    }

    override fun positive(): Value<*> {
        return this
    }

    override fun minus(other: Int): Value<*> {
        return IntegerValue(value - other)
    }

    override fun plus(other: Int): Value<*> {
        return IntegerValue(value + other)
    }

    override fun floorDivide(other: Int): Value<*> {
        return IntegerValue(value / other)
    }

    override fun divide(other: Int): Value<*> {
        return IntegerValue(value / other)
    }

    override fun remainder(other: Int): Value<*> {
        return IntegerValue((value + other) % other)
    }

    override fun times(other: Int): Value<*> {
        return IntegerValue(value * other)
    }

    override fun exponentiate(other: Int): Value<*> {
        return IntegerValue(value.toDouble().pow(other.toDouble()).toInt())
    }

    override fun compareTo(other: Int): Int {
        return value.compareTo(other)
    }

    override fun rangeTo(other: Int): Value<*> {
        return RangeValue(Range(value, other, 1))
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return when (name) {
            "toFloat" -> toFloat()
            else -> super.innerCallFunction(name, local)
        }
    }

    fun toFloat(): Value<*> {
        return FloatValue(value.toFloat())
    }
}
