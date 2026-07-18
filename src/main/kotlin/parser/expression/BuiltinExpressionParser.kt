package parser.expression

import program.expression.Arguments
import program.expression.builtin.*
import program.expression.builtin.list.*
import program.expression.builtin.struct.StructEntriesBuiltin
import program.expression.builtin.struct.StructKeysBuiltin
import program.expression.builtin.struct.StructRemoveBuiltin
import program.expression.builtin.struct.StructValuesBuiltin
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value
import kotlin.reflect.KClass

object BuiltinExpressionParser {
    val builtins: MutableMap<String, (Arguments) -> Builtin> = mutableMapOf()
    val valueBuiltins: MutableMap<KClass<*>, MutableMap<String, (Value<*>, Arguments) -> ValueBuiltin<*>>> = mutableMapOf()

    fun register(name: String, builder: (Arguments) -> Builtin) {
        builtins[name] = builder
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Value<*>> register(name: String, noinline builder: (T, Arguments) -> ValueBuiltin<*>) {
        if (!valueBuiltins.containsKey(T::class)) {
            valueBuiltins[T::class] = mutableMapOf()
        }

        valueBuiltins[T::class]!![name] = builder as (Value<*>, Arguments) -> ValueBuiltin<*>
    }

    fun initialize() {
        register("abs") { arguments -> AbsoluteValueBuiltin(arguments) }
        register("int") { arguments -> IntegerCastBuiltin(arguments) }
        register("float") { arguments -> FloatCastBuiltin(arguments) }
        register("str") { arguments -> StringCastBuiltin(arguments) }
        register("round") { arguments -> RoundBuiltin(arguments) }
        register("floor") { arguments -> FloorBuiltin(arguments) }
        register("ceil") { arguments -> CeilBuiltin(arguments) }
        register("len") { arguments -> LengthBuiltin(arguments) }
        register("print") { arguments -> PrintBuiltin(arguments) }
        register("min") { arguments -> MinimumBuiltin(arguments) }
        register("max") { arguments -> MaximumBuiltin(arguments) }
        register("range") { arguments -> RangeBuiltin(arguments) }
        register("type") { arguments -> TypeBuiltin(arguments) }
        register("wait") { arguments -> WaitBuiltin(arguments) }
        register<ListValue>("append") { value, arguments -> ListAppendBuiltin(value, arguments) }
        register<ListValue>("insert") { value, arguments -> ListInsertBuiltin(value, arguments) }
        register<ListValue>("remove") { value, arguments -> ListRemoveBuiltin(value, arguments) }
        register<ListValue>("pop") { value, arguments -> ListPopBuiltin(value, arguments) }
        register<ListValue>("contains") { value, arguments -> ListContainsBuiltin(value, arguments) }
        register<ListValue>("containsAll") { value, arguments -> ListContainsAllBuiltin(value, arguments) }
        register<StructValue>("remove") { value, arguments -> StructRemoveBuiltin(value, arguments) }
        register<StructValue>("keys") { value, arguments -> StructKeysBuiltin(value, arguments) }
        register<StructValue>("values") { value, arguments -> StructValuesBuiltin(value, arguments) }
        register<StructValue>("entries") { value, arguments -> StructEntriesBuiltin(value, arguments) }
    }
}
