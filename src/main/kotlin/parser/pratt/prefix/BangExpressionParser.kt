package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.BangOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class BangExpressionParser : PrefixParser {
    override val precedence = 6

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.BANG)
        val expression = ExpressionParser.parse(parser, precedence)
        return BangOperator(expression)
    }
}
