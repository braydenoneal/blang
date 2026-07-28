package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Struct

class StructValue(value: Struct) : Value<Struct>(value) {
    override fun typeString(): String = "struct"

    override fun toString(): String {
        return value.variables.toString()
    }

    override fun getItem(program: Program, name: String): Value<*> {
        return value.variables[name] ?: super.getItem(program, name)
    }

    override fun getFunction(program: Program, name: String): ((Program, Arguments) -> Value<*>)? {
        val function = value.definition.functions[name] ?: return super.getFunction(program, name)
        return function.value::call
    }
}
