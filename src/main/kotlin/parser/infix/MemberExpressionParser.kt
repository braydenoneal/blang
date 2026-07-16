package parser.infix

import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.MemberCallExpression
import program.expression.MemberExpression

class MemberExpressionParser(override val precedence: Int) : InfixParser {
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
