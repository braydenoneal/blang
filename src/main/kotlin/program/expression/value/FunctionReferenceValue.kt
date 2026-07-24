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

        return BuiltinFunctions.builtins[value.name]?.invoke(program, arguments) ?: throw RunException("Function ${value.name} does not exist")
    }
}
