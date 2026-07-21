package program.expression

import parser.expression.BuiltinExpressionParser
import program.Program
import program.RunException
import program.expression.value.FunctionValue
import program.expression.value.Value

data class CallExpression(val left: Expression, val arguments: Arguments) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        if (left is IdentifierExpression) {
            for (importStatement in program.imports) {
                if (importStatement.identifiers.last() == left.name) {
                    val importProgram = program.getCustomImportProgram(importStatement)
                    return call(left.name, program, importProgram)
                }
            }

            val function = program.functions[left.name]

            if (function != null) {
                return function.call(program, arguments)
            }

            val variable = program.scope.getNullable(left.name)

            if (variable != null && variable is FunctionValue) {
                return variable.call(program, arguments)
            }
        }

        if (left is DotExpression) {
            val value = left.left.evaluate(program)
            val name = left.right

            val type = value.typeString()
            val valueBuiltin = BuiltinExpressionParser.valueBuiltins[value::class] ?: throw RunException("Type $type does not have any builtins")
            val builtin = valueBuiltin[name] ?: throw RunException("Type $type does not have builtin $name")

            return builtin.invoke(value, arguments).evaluate(program)
        }

        throw RunException("Expression is not callable")
    }

    fun call(name: String, program: Program, functionProgram: Program): Value<*> {
        val function = functionProgram.getFunction(name)

        if (function != null) {
            return function.call(program, arguments)
        }

        val variable = program.scope.get(name)

        if (variable is FunctionValue) {
            return variable.call(program, arguments)
        }

        throw RunException("'$left' does not refer to a function")
    }
}
