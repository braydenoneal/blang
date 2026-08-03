package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.AccessExpression
import program.expression.Expression
import program.expression.SliceExpression

class AccessExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, spanStart: Int, token: Token, left: Expression): Expression {
        val index = ExpressionParser.parse(parser, precedence, true)

        if (parser.peekIs(Type.COLON)) {
            parser.next()
            val to = ExpressionParser.parse(parser, precedence, true)
            parser.expect(Type.RIGHT_SQUARE_BRACE)
            return SliceExpression(left, index, to)
        }

        parser.expect(Type.RIGHT_SQUARE_BRACE)
        return AccessExpression(left, index)
    }
}
