package program.expression

import program.expression.value.BooleanValue
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.Value
import kotlin.reflect.KClass

object UnaryOperators {
    val unaryOperators: MutableMap<KClass<*>, MutableMap<String, (Value<*>) -> Value<*>>> = mutableMapOf()

    fun register(type: KClass<*>, operator: String, result: (Value<*>) -> Value<*>) {
        if (!unaryOperators.containsKey(type)) {
            unaryOperators[type] = mutableMapOf()
        }

        unaryOperators[type]!![operator] = result
    }

    fun initialize() {
        register(IntegerValue::class, "-") { operand -> IntegerValue(-(operand as IntegerValue).value) }
        register(IntegerValue::class, "+") { operand -> IntegerValue((operand as IntegerValue).value) }
        register(FloatValue::class, "-") { operand -> FloatValue(-(operand as FloatValue).value) }
        register(FloatValue::class, "+") { operand -> FloatValue((operand as FloatValue).value) }
        register(BooleanValue::class, "!") { operand -> BooleanValue(!(operand as BooleanValue).value) }
    }
}
