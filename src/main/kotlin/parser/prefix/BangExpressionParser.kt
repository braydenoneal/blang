package parser.prefix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.BangOperator

class BangExpressionParser : PrefixParser {
    override val precedence = 6

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.BANG)
        val expression = ExpressionParser.parse(parser, precedence)
        return BangOperator(expression)
    }
}
