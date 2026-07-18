package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.ListAccessExpression

class ListAccessExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val index = ExpressionParser.parse(parser, precedence, true)
        parser.expect(Type.RIGHT_SQUARE_BRACE)
        return ListAccessExpression(left, index)
    }
}
