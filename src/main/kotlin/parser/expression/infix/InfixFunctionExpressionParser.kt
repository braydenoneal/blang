package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.Arguments
import program.expression.Expression
import program.expression.InfixFunctionExpression

class InfixFunctionExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val right = ExpressionParser.parse(parser, precedence, true)
        val arguments = Arguments(mutableListOf(right), mutableMapOf())
        return InfixFunctionExpression(token.value, left, arguments)
    }
}
