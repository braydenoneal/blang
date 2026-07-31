package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.util.Struct

class StructValue(value: Struct) : Value<Struct>(value) {
    override fun typeString(): String = "struct"

    override fun toString(): String {
        return value.variables.toString()
    }

    override fun getItem(program: Program, name: String): Value<*>? {
        return value.variables[name] ?: super.getItem(program, name)
    }

    override fun assignItem(name: String, setValue: Value<*>): Value<*> {
        if (name in value.variables) {
            value.variables[name] = setValue
            return setValue
        }

        throw RunException("Value does not have variable '$name'")
    }

    override fun getFunction(program: Program, name: String): ((Program, Arguments) -> Value<*>)? {
        val function = value.definition.functions[name] ?: return super.getFunction(program, name)
        return function.value::call
    }

    override fun callFunction(program: Program, arguments: Arguments, name: String): Value<*> {
        arguments.hasSelf = true
        arguments.namedArguments["self"] = this
        return super.callFunction(program, arguments, name)
    }
}
