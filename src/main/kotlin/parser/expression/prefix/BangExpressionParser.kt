package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.operator.BangOperator

class BangExpressionParser(val precedence: Int) : PrefixParser {
    override fun parse(parser: Parser, token: Token, skipNewline: Boolean): Expression {
        val expression = ExpressionParser.parse(parser, precedence)
        return BangOperator(expression)
    }
}
