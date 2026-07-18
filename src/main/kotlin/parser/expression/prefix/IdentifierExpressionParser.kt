package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.IdentifierExpression

class IdentifierExpressionParser : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val name = parser.expect(Type.IDENTIFIER)
        return IdentifierExpression(name)
    }
}
