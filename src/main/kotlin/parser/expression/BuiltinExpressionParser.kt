package parser.expression

import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value
import program.expression.value.builtin.*
import program.expression.value.builtin.list.*
import program.expression.value.builtin.struct.StructEntriesBuiltin
import program.expression.value.builtin.struct.StructKeysBuiltin
import program.expression.value.builtin.struct.StructRemoveBuiltin
import program.expression.value.builtin.struct.StructValuesBuiltin
import kotlin.reflect.KClass

object BuiltinExpressionParser {
    val builtins: MutableMap<String, () -> Builtin> = mutableMapOf()
    val valueBuiltins: MutableMap<KClass<*>, MutableMap<String, (Value<*>) -> ValueBuiltin<*>>> = mutableMapOf()

    fun register(name: String, builder: () -> Builtin) {
        builtins[name] = builder
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Value<*>> register(name: String, noinline builder: (T) -> ValueBuiltin<*>) {
        if (!valueBuiltins.containsKey(T::class)) {
            valueBuiltins[T::class] = mutableMapOf()
        }

        valueBuiltins[T::class]!![name] = builder as (Value<*>) -> ValueBuiltin<*>
    }

    fun initialize() {
        register("abs", ::AbsoluteValueBuiltin)
        register("int", ::IntegerCastBuiltin)
        register("float", ::FloatCastBuiltin)
        register("str", ::StringCastBuiltin)
        register("round", ::RoundBuiltin)
        register("floor", ::FloorBuiltin)
        register("ceil", ::CeilBuiltin)
        register("len", ::LengthBuiltin)
        register("print", ::PrintBuiltin)
        register("min", ::MinimumBuiltin)
        register("max", ::MaximumBuiltin)
        register("range", ::RangeBuiltin)
        register("type", ::TypeBuiltin)
        register("wait", ::WaitBuiltin)
        register<ListValue>("append", ::ListAppendBuiltin)
        register<ListValue>("insert", ::ListInsertBuiltin)
        register<ListValue>("remove", ::ListRemoveBuiltin)
        register<ListValue>("pop", ::ListPopBuiltin)
        register<ListValue>("contains", ::ListContainsBuiltin)
        register<ListValue>("containsAll", ::ListContainsAllBuiltin)
        register<StructValue>("remove", ::StructRemoveBuiltin)
        register<StructValue>("keys", ::StructKeysBuiltin)
        register<StructValue>("values", ::StructValuesBuiltin)
        register<StructValue>("entries", ::StructEntriesBuiltin)
    }
}
