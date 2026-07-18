package program.expression

import parser.expression.BuiltinExpressionParser
import program.Program
import program.RunException
import program.expression.builtin.list.*
import program.expression.builtin.struct.StructEntriesBuiltin
import program.expression.builtin.struct.StructKeysBuiltin
import program.expression.builtin.struct.StructRemoveBuiltin
import program.expression.builtin.struct.StructValuesBuiltin
import program.expression.value.FunctionValue
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class CallExpression(val left: Expression, val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
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
            } else {
                val builtin = BuiltinExpressionParser.builtins[left.name] ?: throw RunException("No function, variable, or builtin exists with name ${left.name}")
                return builtin.invoke(arguments).evaluate(program)
            }
        }

        if (left is DotExpression) {
            val value = left.left.evaluate(program)
            val name = left.right

            if (value is ListValue) {
                when (name) {
                    "append" -> return ListAppendBuiltin(value, arguments).evaluate(program)
                    "insert" -> return ListInsertBuiltin(value, arguments).evaluate(program)
                    "remove" -> return ListRemoveBuiltin(value, arguments).evaluate(program)
                    "pop" -> return ListPopBuiltin(value, arguments).evaluate(program)
                    "contains" -> return ListContainsBuiltin(value, arguments).evaluate(program)
                    "containsAll" -> return ListContainsAllBuiltin(value, arguments).evaluate(program)
                }
            }

            if (value is StructValue) {
                when (name) {
                    "remove" -> return StructRemoveBuiltin(value, arguments).evaluate(program)
                    "keys" -> return StructKeysBuiltin(value, arguments).evaluate(program)
                    "values" -> return StructValuesBuiltin(value, arguments).evaluate(program)
                    "entries" -> return StructEntriesBuiltin(value, arguments).evaluate(program)
                }
            }

            throw RunException("Member is not a variable nor a list")
        }

        throw RunException("Expression is not callable")
    }

    fun call(name: String, program: Program, functionProgram: Program): Value<*>? {
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
