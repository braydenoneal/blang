package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.AssignExpression
import program.expression.Expression

class AssignExpressionParser(override val precedence: Int) : InfixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token, left: Expression): Expression {
        val right = ExpressionParser.parse(precedence, false)
        return AssignExpression(token.value, left, right)
    }
}
