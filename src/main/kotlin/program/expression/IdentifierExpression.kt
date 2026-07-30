package program.expression

import program.Program
import program.expression.value.FunctionReferenceValue
import program.expression.value.Value
import program.expression.value.util.FunctionReference

class IdentifierExpression(val name: String) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        val variable = program.scope.getNullable(name)

        if (variable != null) {
            return variable
        }

        val function = program.functions[name]

        if (function != null) {
            return function
        }

        for (importStatement in program.imports) {
            if (importStatement.name == name) {
                val importProgram = program.getCustomImportProgram(importStatement)
                val function = importProgram.getFunction(name)

                if (function != null) {
                    return function
                }
            }
        }

        return FunctionReferenceValue(FunctionReference(null, name)).withSpan(span)
    }
}
