package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.Arguments
import program.expression.Expression
import program.expression.InfixFunctionExpression

class InfixFunctionExpressionParser(override val precedence: Int) : InfixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token, left: Expression): Expression {
        val right = ExpressionParser.parse(precedence, true)
        val arguments = Arguments(mutableListOf(right), mutableMapOf()).withSpan(spanStart, parser)
        return InfixFunctionExpression(token.value, left, arguments)
    }
}
