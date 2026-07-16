package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.PositiveOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class PositiveExpressionParser : PrefixParser {
    override val precedence = 6

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.PLUS)
        val expression = ExpressionParser.parse(parser, precedence)
        return PositiveOperator(expression)
    }
}
