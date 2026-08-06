package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.booleanvalue.BooleanValue
import program.expression.value.floatvalue.FloatValue
import program.expression.value.function.FunctionValue
import program.expression.value.identifier.IdentifierValue
import program.expression.value.integer.IntegerValue
import program.expression.value.list.ListValue
import program.expression.value.map.MapValue
import program.expression.value.nullvalue.NullValue
import program.expression.value.pair.PairValue
import program.expression.value.range.RangeValue
import program.expression.value.string.StringValue
import program.expression.value.struct.StructValue

abstract class Static<T : Value<*>> : Callable {
    abstract val name: String

    val items = mutableMapOf<String, ValueItem<T>>()
    val functions = mutableMapOf<String, ValueFunction<T>>()
    val staticItems = mutableMapOf<String, StaticItem>()
    val staticFunctions = mutableMapOf<String, StaticFunction>()

    context(program: Program)
    fun getItem(name: String): Value<*>? {
        return staticItems[name]?.get()
    }

    fun register(valueItem: ValueItem<T>) {
        items[valueItem.name] = valueItem
    }

    fun register(valueFunction: ValueFunction<T>) {
        functions[valueFunction.name] = valueFunction
    }

    fun register(valueItem: StaticItem) {
        staticItems[valueItem.name] = valueItem
    }

    fun register(valueFunction: StaticFunction) {
        staticFunctions[valueFunction.name] = valueFunction
    }

    open fun initializeItems() {}

    open fun initializeFunctions() {}

    open fun initializeStaticItems() {}

    open fun initializeStaticFunctions() {}

    fun initialize() {
        register(
            object : ValueFunction<T>() {
                override val name: String = "toString"

                context(program: Program, arguments: Arguments, value: T)
                override fun call(): Value<*> {
                    return StringValue(value.toString())
                }
            },
        )
        register(
            object : ValueFunction<T>() {
                override val name: String = "to"

                context(program: Program, arguments: Arguments, value: T)
                override fun call(): Value<*> {
                    return PairValue(value to getAny("second"))
                }
            },
        )
        initializeItems()
        initializeFunctions()
        initializeStaticItems()
        initializeStaticFunctions()
    }

    companion object {
        val staticCompanions: MutableMap<String, Static<*>> = mutableMapOf()

        fun register(static: Static<*>) {
            staticCompanions[static.name] = static
            static.initialize()
        }

        fun initialize() {
            register(BooleanValue.Companion)
            register(FloatValue.Companion)
            register(FunctionValue.Companion)
            register(IdentifierValue.Companion)
            register(IntegerValue.Companion)
            register(ListValue.Companion)
            register(MapValue.Companion)
            register(NullValue.Companion)
            register(PairValue.Companion)
            register(RangeValue.Companion)
            register(StringValue.Companion)
            register(StructValue.Companion)
        }
    }
}
