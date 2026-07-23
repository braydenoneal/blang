package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.Callable

class CallableValue(value: Callable) : Value<Callable>(value) {
    override fun typeString(): String = "callable"

    fun call(program: Program, arguments: Arguments): Value<*> {
        return value.call(program, arguments)
    }

    override fun toString(): String {
        // TODO: Implement
        return "callable"
    }
}
