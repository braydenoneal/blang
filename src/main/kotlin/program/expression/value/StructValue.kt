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

    override fun getItem(program: Program, name: String): Value<*> {
        return value.variables[name] ?: value.definition.staticVariables[name]?.evaluate(program) ?: super.getItem(program, name)
    }

    override fun assignItem(name: String, setValue: Value<*>): Value<*> {
        if (name in value.variables) {
            value.variables[name] = setValue
            return setValue
        }

        throw RunException("Value does not have variable '$name'")
    }

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        arguments.hasSelf = true
        arguments.namedArguments["self"] = this
        val function = value.definition.functions[name] ?: value.definition.staticFunctions[name] ?: return super.innerCallFunction(program, arguments, name, local)
        return function.value.call(program, arguments)
    }
}
