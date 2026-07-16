package parser.infix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.IfElseExpression

class ConditionalExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.IF_KEYWORD)
        val middle = ExpressionParser.parse(parser, precedence)
        parser.expect(Type.KEYWORD, "else")
        val right = ExpressionParser.parse(parser, precedence - 1)
        return IfElseExpression(middle, left, right)
    }
}
