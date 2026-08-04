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

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        return when (name) {
            "toFloat" -> toFloat(program, arguments)
            else -> super.innerCallFunction(program, arguments, name, local)
        }
    }

    fun toFloat(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return FloatValue(value.toFloat())
    }
}
