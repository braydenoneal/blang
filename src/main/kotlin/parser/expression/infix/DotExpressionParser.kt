package parser.expression.infix

import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.DotExpression
import program.expression.Expression

class DotExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, spanStart: Int, token: Token, left: Expression): Expression {
        val right = parser.expect(Type.IDENTIFIER)
        val local = token.type == Type.DOT
        return DotExpression(left, right, local)
    }
}
