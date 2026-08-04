package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.BinaryOperatorExpression
import program.expression.Expression

class BinaryOperatorExpressionParser(override val precedence: Int) : InfixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token, left: Expression): Expression {
        val right = ExpressionParser.parse(precedence)
        return BinaryOperatorExpression(token.value, left, right)
    }
}
