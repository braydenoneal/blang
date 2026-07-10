package parser.expression

import parser.Parser
import parser.Program
import parser.expression.builtin.BuiltinExpression
import parser.expression.value.Value
import tokenizer.Type

data class VariableExpression(val name: String) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return program.scope.get(name)
    }

    companion object {
        fun parse(parser: Parser): Expression {
            val token = parser.next()

            if (parser.peekIs(Type.PARENTHESIS, "(")) {
                return BuiltinExpression.parse(parser, token.value)
            }

            return VariableExpression(token.value)
        }
    }
}
