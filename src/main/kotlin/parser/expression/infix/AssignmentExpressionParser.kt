package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import program.expression.AssignmentExpression
import program.expression.Expression

class AssignmentExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        val operator = parser.next()
        val right = ExpressionParser.parse(parser, precedence)
        return AssignmentExpression(operator.value, left, right)
    }
}
