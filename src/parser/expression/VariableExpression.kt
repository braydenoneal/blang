package parser.expression

import parser.Program
import parser.RunException
import parser.expression.builtin.BuiltinExpression
import parser.expression.value.Value
import tokenizer.Type

data class VariableExpression(val name: String) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return program.scope.get(name) ?: run { throw RunException("Variable with name '$name' does not exist") }
    }

    companion object {
        fun parse(program: Program): Expression {
            val token = program.next()

            if (program.peekIs(Type.PARENTHESIS, "(")) {
                return BuiltinExpression.parse(program, token.value)
            }

            return VariableExpression(token.value)
        }
    }
}
