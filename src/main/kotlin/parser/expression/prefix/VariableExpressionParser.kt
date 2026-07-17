package parser.expression.prefix

import parser.Parser
import parser.expression.BuiltinExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.VariableExpression

class VariableExpressionParser : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val name = parser.expect(Type.IDENTIFIER)

        if (parser.peekIs(Type.LEFT_PARENTHESIS)) {
            return BuiltinExpressionParser.parse(parser, name)
        }

        return VariableExpression(name)
    }
}
