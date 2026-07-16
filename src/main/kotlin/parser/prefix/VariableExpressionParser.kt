package parser.prefix

import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.VariableExpression
import program.expression.builtin.BuiltinExpression

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
