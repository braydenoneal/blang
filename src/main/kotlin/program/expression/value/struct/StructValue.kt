package program.expression.value.struct

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value

class StructValue(value: Struct) : Value<Struct>(value) {
    override fun toString(): String {
        return value.variables.toString()
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return value.variables[name] ?: value.definition.staticVariables[name]?.evaluate() ?: super.getItem(name)
    }

    override fun assignItem(name: String, setValue: Value<*>): Value<*> {
        if (name in value.variables) {
            value.variables[name] = setValue
            return setValue
        }

        throw RunException("Value does not have variable '$name'")
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        arguments.hasSelf = true
        arguments.namedArguments["self"] = this
        val function = value.definition.functions[name] ?: value.definition.staticFunctions[name] ?: return super.innerCallFunction(name, local)
        return function.value.call()
    }

    override val static = Companion

    companion object : Static<StructValue>() {
        override val name = "Struct"
    }
}
