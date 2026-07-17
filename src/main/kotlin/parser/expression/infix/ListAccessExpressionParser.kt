package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.ListAccessExpression

class ListAccessExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.LEFT_SQUARE_BRACE)
        val index = ExpressionParser.parse(parser, precedence, true)
        parser.expect(Type.RIGHT_SQUARE_BRACE)
        return ListAccessExpression(left, index)
    }
}
