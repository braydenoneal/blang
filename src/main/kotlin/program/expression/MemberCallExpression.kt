package program.expression

import parser.Parser
import program.Program
import program.RunException
import program.expression.builtin.list.*
import program.expression.builtin.struct.StructEntriesBuiltin
import program.expression.builtin.struct.StructKeysBuiltin
import program.expression.builtin.struct.StructRemoveBuiltin
import program.expression.builtin.struct.StructValuesBuiltin
import program.expression.value.ListValue
import program.expression.value.StructValue
import program.expression.value.Value

data class MemberCallExpression(
    val member: Expression,
    val functionName: String,
    val arguments: Arguments,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        var value: Value<*>

        if (member is VariableExpression) {
            for (importStatement in program.imports) {
                if (importStatement.identifiers.last() == member.name) {
                    val importProgram = program.getCustomImportProgram(importStatement)
                    val callExpression = CallExpression(functionName, arguments)
                    return callExpression.call(program, importProgram)
                }
            }

            value = program.scope.get(member.name)
        } else {
            value = member.evaluate(program) ?: return null
        }

        if (value is ListValue) {
            when (functionName) {
                "append" -> return ListAppendBuiltin(value, arguments).evaluate(program)
                "insert" -> return ListInsertBuiltin(value, arguments).evaluate(program)
                "remove" -> return ListRemoveBuiltin(value, arguments).evaluate(program)
                "pop" -> return ListPopBuiltin(value, arguments).evaluate(program)
                "contains" -> return ListContainsBuiltin(value, arguments).evaluate(program)
                "containsAll" -> return ListContainsAllBuiltin(value, arguments).evaluate(program)
            }
        }

        if (value is StructValue) {
            when (functionName) {
                "remove" -> return StructRemoveBuiltin(value, arguments).evaluate(program)
                "keys" -> return StructKeysBuiltin(value, arguments).evaluate(program)
                "values" -> return StructValuesBuiltin(value, arguments).evaluate(program)
                "entries" -> return StructEntriesBuiltin(value, arguments).evaluate(program)
            }
        }

        throw RunException("Member is not a variable nor a list")
    }

    companion object {
        fun parse(parser: Parser, member: Expression, functionName: String): Expression {
            val arguments: Arguments = Arguments.parse(parser)
            return MemberCallExpression(member, functionName, arguments)
        }
    }
}
