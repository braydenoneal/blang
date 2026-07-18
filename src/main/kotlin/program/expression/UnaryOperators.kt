package program.expression

import program.expression.value.BooleanValue
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.reflect.KClass

object UnaryOperators {
    val unaryOperators: MutableMap<KClass<*>, MutableMap<String, (Value<*>) -> Value<*>>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Value<*>> register(operator: String, noinline result: (T) -> Value<*>) {
        if (!unaryOperators.containsKey(T::class)) {
            unaryOperators[T::class] = mutableMapOf()
        }

        unaryOperators[T::class]!![operator] = result as (Value<*>) -> Value<*>
    }

    fun initialize() {
        register<IntegerValue>("-") { operand -> IntegerValue(-operand.value) }
        register<IntegerValue>("+") { operand -> IntegerValue(operand.value) }
        register<FloatValue>("-") { operand -> FloatValue(-operand.value) }
        register<FloatValue>("+") { operand -> FloatValue(operand.value) }
        register<BooleanValue>("!") { operand -> BooleanValue(!operand.value) }
    }
}
