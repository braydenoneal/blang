package program.expression

import program.expression.value.*
import kotlin.math.floor
import kotlin.math.pow
import kotlin.reflect.KClass

object BinaryOperators {
    val binaryOperators: MutableMap<KClass<*>, MutableMap<String, (Value<*>, Value<*>) -> Value<*>>> = mutableMapOf()

    fun register(type: KClass<*>, operator: String, result: (Value<*>, Value<*>) -> Value<*>) {
        if (!binaryOperators.containsKey(type)) {
            binaryOperators[type] = mutableMapOf()
        }

        binaryOperators[type]!![operator] = result
    }

    fun initialize() {
        register(IntegerValue::class, "+") { left, right -> IntegerValue((left as IntegerValue).value + (right as IntegerValue).value) }
        register(IntegerValue::class, "-") { left, right -> IntegerValue((left as IntegerValue).value - (right as IntegerValue).value) }
        register(IntegerValue::class, "*") { left, right -> IntegerValue((left as IntegerValue).value * (right as IntegerValue).value) }
        register(IntegerValue::class, "//") { left, right -> IntegerValue((left as IntegerValue).value / (right as IntegerValue).value) }
        register(IntegerValue::class, "/") { left, right -> IntegerValue((left as IntegerValue).value / (right as IntegerValue).value) }
        register(IntegerValue::class, "%") { left, right -> IntegerValue(((left as IntegerValue).value + (right as IntegerValue).value) % right.value) }
        register(IntegerValue::class, "<=") { left, right -> BooleanValue((left as IntegerValue).value <= (right as IntegerValue).value) }
        register(IntegerValue::class, ">=") { left, right -> BooleanValue((left as IntegerValue).value >= (right as IntegerValue).value) }
        register(IntegerValue::class, "<") { left, right -> BooleanValue((left as IntegerValue).value < (right as IntegerValue).value) }
        register(IntegerValue::class, ">") { left, right -> BooleanValue((left as IntegerValue).value > (right as IntegerValue).value) }
        register(IntegerValue::class, "^") { left, right -> IntegerValue((left as IntegerValue).value.toDouble().pow((right as IntegerValue).value.toDouble()).toInt()) }
        register(FloatValue::class, "+") { left, right -> FloatValue((left as FloatValue).value + (right as FloatValue).value) }
        register(FloatValue::class, "-") { left, right -> FloatValue((left as FloatValue).value - (right as FloatValue).value) }
        register(FloatValue::class, "*") { left, right -> FloatValue((left as FloatValue).value * (right as FloatValue).value) }
        register(FloatValue::class, "//") { left, right -> FloatValue(floor(((left as FloatValue).value / (right as FloatValue).value).toDouble()).toFloat()) }
        register(FloatValue::class, "/") { left, right -> FloatValue((left as FloatValue).value / (right as FloatValue).value) }
        register(FloatValue::class, "%") { left, right -> FloatValue(((left as FloatValue).value + (right as FloatValue).value) % right.value) }
        register(FloatValue::class, "<=") { left, right -> BooleanValue((left as FloatValue).value <= (right as FloatValue).value) }
        register(FloatValue::class, ">=") { left, right -> BooleanValue((left as FloatValue).value >= (right as FloatValue).value) }
        register(FloatValue::class, "<") { left, right -> BooleanValue((left as FloatValue).value < (right as FloatValue).value) }
        register(FloatValue::class, ">") { left, right -> BooleanValue((left as FloatValue).value > (right as FloatValue).value) }
        register(FloatValue::class, "^") { left, right -> FloatValue((left as FloatValue).value.toDouble().pow((right as FloatValue).value.toDouble()).toFloat()) }
        register(StringValue::class, "+") { left, right -> StringValue((left as StringValue).value + (right as StringValue).value) }
        register(ListValue::class, "+") { left, right -> ListValue((left as ListValue).value.plus((right as ListValue).value).toMutableList()) }
        register(BooleanValue::class, "and") { left, right -> BooleanValue((left as BooleanValue).value && (right as BooleanValue).value) }
        register(BooleanValue::class, "or") { left, right -> BooleanValue((left as BooleanValue).value || (right as BooleanValue).value) }
    }
}
