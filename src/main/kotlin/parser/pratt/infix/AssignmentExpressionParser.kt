package parser.pratt.infix

import parser.Parser
import parser.expression.AssignmentExpression
import parser.expression.Expression
import parser.pratt.ExpressionParser

class AssignmentExpressionParser : InfixParser {
    override val precedence = 1

    override fun parse(parser: Parser, left: Expression): Expression {
        val operator = parser.next()
        val right = ExpressionParser.parse(parser, precedence)
        return AssignmentExpression(operator.value, left, right)
    }
}
