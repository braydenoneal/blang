package parser.expression.infix

import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.DotExpression
import program.expression.Expression

class DotExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val right = parser.expect(Type.IDENTIFIER)
        return DotExpression(left, right)
    }
}
