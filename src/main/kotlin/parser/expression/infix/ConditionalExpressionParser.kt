package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.IfElseExpression

class ConditionalExpressionParser(override val precedence: Int) : InfixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token, left: Expression): Expression {
        val middle = ExpressionParser.parse(precedence)
        parser.expect(Type.ELSE_KEYWORD)
        val right = ExpressionParser.parse(precedence - 1)
        return IfElseExpression(middle, left, right)
    }
}
