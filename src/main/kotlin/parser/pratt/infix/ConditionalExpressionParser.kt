package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.IfElseExpression
import parser.pratt.ExpressionParser
import tokenizer.Type

class ConditionalExpressionParser : InfixParser {
    override val precedence = 2

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.IF_KEYWORD)
        val middle = ExpressionParser.parse(parser, precedence)
        parser.expect(Type.KEYWORD, "else")
        val right = ExpressionParser.parse(parser, precedence - 1)
        return IfElseExpression(middle, left, right)
    }
}
