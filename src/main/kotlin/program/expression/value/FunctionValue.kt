package program.expression.value

import program.Program
import program.expression.Arguments

class FunctionValue(value: Funct) : Value<Funct>(value) {
    override fun typeString(): String = "function"

    fun call(program: Program, arguments: Arguments): Value<*> {
        return value.call(program, arguments)
    }

    override fun toString(): String {
        return "fn" + value.parameters.toString() + ": " + value.statements.toString()
    }
}
