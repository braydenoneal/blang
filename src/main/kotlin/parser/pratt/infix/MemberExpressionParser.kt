package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.MemberCallExpression
import parser.expression.MemberExpression
import tokenizer.Type

class MemberExpressionParser : InfixParser {
    override val precedence = 7

    override fun parse(parser: Parser, left: Expression): Expression {
        var expression = left

        // TODO: Parse one x.y at a time and allow MemberExpression to handle its left being another MemberExpression for nested members
        while (parser.peekIs(Type.DOT)) {
            parser.expect(Type.DOT)
            val name = parser.expect(Type.IDENTIFIER)

            expression = if (parser.peekIs(Type.LEFT_PARENTHESIS)) {
                MemberCallExpression.parse(parser, expression, name)
            } else {
                MemberExpression(expression, name)
            }
        }

        return expression
    }
}
