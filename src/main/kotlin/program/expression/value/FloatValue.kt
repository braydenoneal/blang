package program.expression.value

import program.Program
import program.expression.Arguments
import kotlin.math.floor
import kotlin.math.pow

class FloatValue(value: Float) : Value<Float>(value) {
    override fun typeString(): String = "float"

    override fun negative(): Value<*> {
        return FloatValue(-value)
    }

    override fun positive(): Value<*> {
        return this
    }

    override fun minus(other: Float): Value<*> {
        return FloatValue(value - other)
    }

    override fun plus(other: Float): Value<*> {
        return FloatValue(value + other)
    }

    override fun floorDivide(other: Float): Value<*> {
        return FloatValue(floor((value / other).toDouble()).toFloat())
    }

    override fun divide(other: Float): Value<*> {
        return FloatValue(value / other)
    }

    override fun remainder(other: Float): Value<*> {
        return FloatValue((value + other) % other)
    }

    override fun times(other: Float): Value<*> {
        return FloatValue(value * other)
    }

    override fun exponentiate(other: Float): Value<*> {
        return FloatValue(value.toDouble().pow(other.toDouble()).toFloat())
    }

    override fun compareTo(other: Float): Int {
        return value.compareTo(other)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return when (name) {
            "toInt" -> toInt()
            else -> super.innerCallFunction(name, local)
        }
    }

    fun toInt(): Value<*> {
        return IntegerValue(value.toInt())
    }
}
