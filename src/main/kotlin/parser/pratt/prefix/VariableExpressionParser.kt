package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression
import parser.expression.VariableExpression
import parser.expression.builtin.BuiltinExpression
import tokenizer.Type

class VariableExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val name = parser.expect(Type.IDENTIFIER)

        if (parser.peekIs(Type.LEFT_PARENTHESIS)) {
            return BuiltinExpression.parse(parser, name)
        }

        return VariableExpression(name)
    }
}
