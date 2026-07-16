package parser.prefix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression

class GroupExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.LEFT_PARENTHESIS)
        val expression = ExpressionParser.parse(parser, precedence, true)
        parser.expect(Type.RIGHT_PARENTHESIS)
        return expression
    }
}
