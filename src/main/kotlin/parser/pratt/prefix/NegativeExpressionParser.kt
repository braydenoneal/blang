package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.NegativeOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class NegativeExpressionParser : PrefixParser {
    override val precedence = 6

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.MINUS)
        val expression = ExpressionParser.parse(parser, precedence)
        return NegativeOperator(expression)
    }
}
