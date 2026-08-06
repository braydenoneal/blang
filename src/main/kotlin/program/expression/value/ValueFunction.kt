package program.expression.value

import program.Program
import program.expression.Arguments

abstract class ValueFunction<T : Value<*>> {
    abstract val name: String

    context(program: Program, arguments: Arguments, value: T)
    abstract fun call(): Value<*>

    context(program: Program, arguments: Arguments)
    fun getAny(name: String, default: Value<*>? = null): Value<*> {
        return arguments.getAny(name, default)
    }

    context(program: Program, arguments: Arguments)
    inline fun <reified T : Value<*>> get(name: String, default: T? = null): T {
        return arguments.get<T>(name, default)
    }

    context(program: Program, arguments: Arguments)
    inline fun <reified T : Value<*>> getNullable(name: String): T? {
        return arguments.getNullable<T>(name)
    }
}
