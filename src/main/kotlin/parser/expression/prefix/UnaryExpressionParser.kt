package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.UnaryOperatorExpression

class UnaryExpressionParser(val precedence: Int) : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
        val expression = ExpressionParser.parse(precedence)
        return UnaryOperatorExpression(token.value, expression)
    }
}
