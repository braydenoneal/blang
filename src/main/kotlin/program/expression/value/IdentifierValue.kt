package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.BuiltinFunctions
import program.expression.value.util.Struct

class IdentifierValue(value: String) : Value<String>(value) {
    override fun typeString(): String = "identifier"

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        // Struct static variable
        val structDefinition = program.getStruct(value)

        if (structDefinition != null) {
            val item = structDefinition.staticVariables[name]

            if (item != null) {
                return item.evaluate()
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

        return super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        // Struct static function
        val structDefinition = program.getStruct(value)

        if (structDefinition != null) {
            val item = structDefinition.staticFunctions[name]

            if (item != null) {
                return item.value.call()
            }
        }

        // Imported namespace function
        for (importStatement in program.imports) {
            if (importStatement.name == value) {
                val importProgram = program.getCustomImportProgram(importStatement)
                val function = importProgram.functions[name]

                context(importProgram) {
                    if (function != null) {
                        if (local) {
                            importProgram.actionProgram = program
                            val value = function.value.call()
                            importProgram.actionProgram = importProgram
                            return value
                        }

                        return function.value.call()
                    }
                }

                break
            }
        }

        // Builtin static function
        val staticCompanion = Static.staticCompanions[value]

        if (staticCompanion != null) {
            return staticCompanion.innerCallFunction(name)
        }

        return super.innerCallFunction(name, local)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        // Struct constructor
        val structDefinition = program.getStruct(value)

        if (structDefinition != null) {
            val variables = mutableMapOf<String, Value<*>>()

            for (name in structDefinition.parameters) {
                variables[name] = arguments.getAny(name)
            }

            for ((name, default) in structDefinition.defaultParameters) {
                variables[name] = arguments.getAny(name, default.evaluate())
            }

            return StructValue(Struct(structDefinition, variables))
        }

        // Function
        val function = program.functions[value]

        if (function != null) {
            return function.call()
        }

        // Builtin constructor
        val staticCompanion = Static.staticCompanions[value]

        if (staticCompanion != null) {
            return staticCompanion.call()
        }

        // Builtin function
        return BuiltinFunctions.builtins[value]?.invoke(program, arguments) ?: throw RunException("Identifier '$value' does not refer to anything", span)
    }
}
