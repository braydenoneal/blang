package program.expression

import parser.expression.BuiltinExpressionParser
import program.Program
import program.RunException
import program.expression.value.CallableValue
import program.expression.value.Value

data class IdentifierExpression(val name: String) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val variable = program.scope.getNullable(name)

        if (variable != null) {
            return variable
        }

        val function = program.functions[name]

        if (function != null) {
            return CallableValue(function.function)
        }

        for (importStatement in program.imports) {
            if (importStatement.name == name) {
                val importProgram = program.getCustomImportProgram(importStatement)
                val function = importProgram.getFunction(name)

                if (function != null) {
                    return CallableValue(function.function)
                }
            }
        }

        val builder = BuiltinExpressionParser.builtins[name]

        if (builder != null) {
            return CallableValue(builder.invoke())
        }

        throw RunException("Identifier doesn't refer to anything")
    }
}
