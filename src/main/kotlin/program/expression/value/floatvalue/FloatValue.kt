package program.expression.value.floatvalue

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value
import kotlin.math.floor
import kotlin.math.pow

class FloatValue(value: Float) : Value<Float>(value) {
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

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<FloatValue>() {
        override val name = "Float"

        override fun initializeFunctions() {
            register(ToIntFunction)
        }
    }
}
