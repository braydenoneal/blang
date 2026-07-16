package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression
import parser.pratt.ExpressionParser
import tokenizer.Type

class GroupExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.LEFT_PARENTHESIS)
        val expression = ExpressionParser.parse(parser, precedence, true)
        parser.expect(Type.RIGHT_PARENTHESIS)
        return expression
    }
}
