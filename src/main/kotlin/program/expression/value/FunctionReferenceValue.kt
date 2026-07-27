package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.BuiltinFunctions
import program.expression.value.util.FunctionReference

class FunctionReferenceValue(value: FunctionReference) : Value<FunctionReference>(value) {
    override fun typeString(): String = "functionReference"

    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        if (value.value != null) {
            return value.value.callFunction(program, arguments, value.name)
        }

        val staticCompanion = Static.staticCompanions[value.name]

        if (staticCompanion != null) {
            return staticCompanion.constructor(program, arguments)
        }

        return BuiltinFunctions.builtins[value.name]?.invoke(program, arguments) ?: throw RunException("Function ${value.name} does not exist")
    }

    override fun getItem(name: String): Value<*> {
        val staticCompanion = Static.staticCompanions[value.name]

        if (staticCompanion != null) {
            val item = staticCompanion.getItem(name)

            if (item != null) {
                return item
            }
        }

        return super.getItem(name)
    }

    override fun getFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        val staticCompanion = Static.staticCompanions[value.name]

        if (staticCompanion != null) {
            return staticCompanion.getFunction(name)
        }

        return super.getFunction(name)
    }
}
