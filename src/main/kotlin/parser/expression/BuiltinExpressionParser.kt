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

object BuiltinExpressionParser {
    val builtins: MutableMap<String, (Arguments) -> Builtin> = mutableMapOf()
    val valueBuiltins: MutableMap<String, MutableMap<String, (Value<*>, Arguments) -> ValueBuiltin<*>>> = mutableMapOf()

    fun register(name: String, builder: (Arguments) -> Builtin) {
        builtins[name] = builder
    }

    fun register(type: String, name: String, builder: (Value<*>, Arguments) -> ValueBuiltin<*>) {
        if (!valueBuiltins.containsKey(type)) {
            valueBuiltins[type] = mutableMapOf()
        }

        valueBuiltins[type]!![name] = builder
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
        register("list", "append") { value, arguments -> ListAppendBuiltin(value as ListValue, arguments) }
        register("list", "insert") { value, arguments -> ListInsertBuiltin(value as ListValue, arguments) }
        register("list", "remove") { value, arguments -> ListRemoveBuiltin(value as ListValue, arguments) }
        register("list", "pop") { value, arguments -> ListPopBuiltin(value as ListValue, arguments) }
        register("list", "contains") { value, arguments -> ListContainsBuiltin(value as ListValue, arguments) }
        register("list", "containsAll") { value, arguments -> ListContainsAllBuiltin(value as ListValue, arguments) }
        register("struct", "remove") { value, arguments -> StructRemoveBuiltin(value as StructValue, arguments) }
        register("struct", "keys") { value, arguments -> StructKeysBuiltin(value as StructValue, arguments) }
        register("struct", "values") { value, arguments -> StructValuesBuiltin(value as StructValue, arguments) }
        register("struct", "entries") { value, arguments -> StructEntriesBuiltin(value as StructValue, arguments) }
    }
}
