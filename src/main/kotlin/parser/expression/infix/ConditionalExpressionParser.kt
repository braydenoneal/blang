package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.IfElseExpression

class ConditionalExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val middle = ExpressionParser.parse(parser, precedence)
        parser.expect(Type.ELSE_KEYWORD)
        val right = ExpressionParser.parse(parser, precedence - 1)
        return IfElseExpression(middle, left, right)
    }
}
