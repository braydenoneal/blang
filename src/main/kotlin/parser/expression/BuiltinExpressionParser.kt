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
