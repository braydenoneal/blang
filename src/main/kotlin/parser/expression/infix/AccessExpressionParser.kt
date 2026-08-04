package parser.expression.infix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.AccessExpression
import program.expression.Expression
import program.expression.SliceExpression

class AccessExpressionParser(override val precedence: Int) : InfixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token, left: Expression): Expression {
        val index = if (parser.peekIs(Type.COLON)) null else ExpressionParser.parse(precedence, true)

        if (parser.peekIs(Type.COLON)) {
            parser.next()
            val to = if (parser.peekIs(Type.RIGHT_SQUARE_BRACE)) null else ExpressionParser.parse(precedence, true)
            parser.expect(Type.RIGHT_SQUARE_BRACE)
            return SliceExpression(left, index, to)
        }

        parser.expect(Type.RIGHT_SQUARE_BRACE)
        return AccessExpression(left, index ?: throw ParseException("Access expression must have an index"))
    }
}
