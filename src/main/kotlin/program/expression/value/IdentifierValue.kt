package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.BuiltinFunctions
import program.expression.value.util.Struct

class IdentifierValue(value: String) : Value<String>(value) {
    override fun typeString(): String = "identifier"

    override fun getItem(program: Program, name: String): Value<*> {
        // Struct static variable
        val structDefinition = program.getStruct(value)

        if (structDefinition != null) {
            val item = structDefinition.staticVariables[name]

            if (item != null) {
                return item.evaluate(program)
            }
        }

        // Imported namespace variable
        for (importStatement in program.imports) {
            if (importStatement.name == value) {
                val importProgram = program.getCustomImportProgram(importStatement)
                val variable = importProgram.topScope.variables[name]

                if (variable != null) {
                    return variable
                }

                break
            }
        }

        // Builtin static variable
        val staticCompanion = Static.staticCompanions[value]

        if (staticCompanion != null) {
            val item = staticCompanion.getItem(name)

            if (item != null) {
                return item
            }
        }

        return super.getItem(program, name)
    }

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        // Struct static function
        val structDefinition = program.getStruct(value)

        if (structDefinition != null) {
            val item = structDefinition.staticFunctions[name]

            if (item != null) {
                return item.value.call(program, arguments)
            }
        }

        // Imported namespace function
        for (importStatement in program.imports) {
            if (importStatement.name == value) {
                val importProgram = program.getCustomImportProgram(importStatement)
                val function = importProgram.functions[name]

                if (function != null) {
                    if (local) {
                        importProgram.actionProgram = program
                        val value = function.value.call(importProgram, arguments)
                        importProgram.actionProgram = importProgram
                        return value
                    }

                    return function.value.call(importProgram, arguments)
                }

                break
            }
        }

        // Builtin static function
        val staticCompanion = Static.staticCompanions[value]

        if (staticCompanion != null) {
            return staticCompanion.innerCallFunction(program, arguments, name)
        }

        return super.innerCallFunction(program, arguments, name, local)
    }

    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        // Struct constructor
        val structDefinition = program.getStruct(value)

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

        // Function
        val function = program.functions[value]

        if (function != null) {
            return function.call(program, arguments)
        }

        // Builtin constructor
        val staticCompanion = Static.staticCompanions[value]

        if (staticCompanion != null) {
            return staticCompanion.call(program, arguments)
        }

        // Builtin function
        return BuiltinFunctions.builtins[value]?.invoke(program, arguments) ?: throw RunException("Identifier '$value' does not refer to anything", span)
    }
}
