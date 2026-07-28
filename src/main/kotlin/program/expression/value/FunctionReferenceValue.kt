package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.BuiltinFunctions
import program.expression.value.util.FunctionReference
import program.expression.value.util.Struct

class FunctionReferenceValue(value: FunctionReference) : Value<FunctionReference>(value) {
    override fun typeString(): String = "functionReference"

    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        if (value.value != null) {
            if (value.value is StructValue) {
                arguments.hasSelf = true
                arguments.namedArguments["self"] = value.value
            }

            return value.value.callFunction(program, arguments, value.name)
        }

        val structDefinition = program.getStruct(value.name)

        if (structDefinition != null) {
            val variables = mutableMapOf<String, Value<*>>()

            for (name in structDefinition.parameters) {
                variables[name] = arguments.getAny(program, name)
            }

            for ((name, default) in structDefinition.defaultParameters) {
                variables[name] = arguments.getAny(program, name, default.evaluate(program))
            }

            return StructValue(Struct(structDefinition, variables))
        }

        val staticCompanion = Static.staticCompanions[value.name]

        if (staticCompanion != null) {
            return staticCompanion.constructor(program, arguments)
        }

        return BuiltinFunctions.builtins[value.name]?.invoke(program, arguments) ?: throw RunException("Function ${value.name} does not exist")
    }

    override fun getItem(program: Program, name: String): Value<*> {
        val structDefinition = program.getStruct(value.name)

        if (structDefinition != null) {
            val item = structDefinition.staticVariables[name]

            if (item != null) {
                return item.evaluate(program)
            }
        }

        val staticCompanion = Static.staticCompanions[value.name]

        if (staticCompanion != null) {
            val item = staticCompanion.getItem(name)

            if (item != null) {
                return item
            }
        }

        return super.getItem(program, name)
    }

    override fun getFunction(program: Program, name: String): ((Program, Arguments) -> Value<*>)? {
        val structDefinition = program.getStruct(value.name)

        if (structDefinition != null) {
            val item = structDefinition.staticFunctions[name]

            if (item != null) {
                return item.value::call
            }
        }

        val staticCompanion = Static.staticCompanions[value.name]

        if (staticCompanion != null) {
            return staticCompanion.getFunction(name)
        }

        return super.getFunction(program, name)
    }
}
