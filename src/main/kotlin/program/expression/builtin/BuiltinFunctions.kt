package program.expression.builtin

import program.expression.value.Callable

object BuiltinFunctions {
    val builtins = mutableMapOf<String, Callable>()

    fun register(name: String, callable: Callable) {
        builtins[name] = callable
    }

    fun initialize() {
        register("abs", AbsoluteValue)
        register("round", Round)
        register("floor", Floor)
        register("ceil", Ceil)
        register("print", Print)
        register("min", Minimum)
        register("max", Maximum)
        register("type", Type)
        register("wait", Wait)
    }
}
