package program.expression

import program.expression.value.*
import kotlin.math.floor
import kotlin.math.pow
import kotlin.reflect.KClass

object BinaryOperators {
    val binaryOperators: MutableMap<KClass<*>, MutableMap<String, (Value<*>, Value<*>) -> Value<*>>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Value<*>> register(operator: String, noinline result: (T, T) -> Value<*>) {
        if (!binaryOperators.containsKey(T::class)) {
            binaryOperators[T::class] = mutableMapOf()
        }

        binaryOperators[T::class]!![operator] = result as (Value<*>, Value<*>) -> Value<*>
    }

    fun initialize() {
        register<IntegerValue>("+") { left, right -> IntegerValue(left.value + right.value) }
        register<IntegerValue>("-") { left, right -> IntegerValue(left.value - right.value) }
        register<IntegerValue>("*") { left, right -> IntegerValue(left.value * right.value) }
        register<IntegerValue>("//") { left, right -> IntegerValue(left.value / right.value) }
        register<IntegerValue>("/") { left, right -> IntegerValue(left.value / right.value) }
        register<IntegerValue>("%") { left, right -> IntegerValue((left.value + right.value) % right.value) }
        register<IntegerValue>("<=") { left, right -> BooleanValue(left.value <= right.value) }
        register<IntegerValue>(">=") { left, right -> BooleanValue(left.value >= right.value) }
        register<IntegerValue>("<") { left, right -> BooleanValue(left.value < right.value) }
        register<IntegerValue>(">") { left, right -> BooleanValue(left.value > right.value) }
        register<IntegerValue>("^") { left, right -> IntegerValue(left.value.toDouble().pow(right.value.toDouble()).toInt()) }
        register<FloatValue>("+") { left, right -> FloatValue(left.value + right.value) }
        register<FloatValue>("-") { left, right -> FloatValue(left.value - right.value) }
        register<FloatValue>("*") { left, right -> FloatValue(left.value * right.value) }
        register<FloatValue>("//") { left, right -> FloatValue(floor((left.value / right.value).toDouble()).toFloat()) }
        register<FloatValue>("/") { left, right -> FloatValue(left.value / right.value) }
        register<FloatValue>("%") { left, right -> FloatValue((left.value + right.value) % right.value) }
        register<FloatValue>("<=") { left, right -> BooleanValue(left.value <= right.value) }
        register<FloatValue>(">=") { left, right -> BooleanValue(left.value >= right.value) }
        register<FloatValue>("<") { left, right -> BooleanValue(left.value < right.value) }
        register<FloatValue>(">") { left, right -> BooleanValue(left.value > right.value) }
        register<FloatValue>("^") { left, right -> FloatValue(left.value.toDouble().pow(right.value.toDouble()).toFloat()) }
        register<StringValue>("+") { left, right -> StringValue(left.value + right.value) }
        register<ListValue>("+") { left, right -> ListValue(left.value.plus(right.value).toMutableList()) }
        register<BooleanValue>("and") { left, right -> BooleanValue(left.value && right.value) }
        register<BooleanValue>("or") { left, right -> BooleanValue(left.value || right.value) }
    }
}
