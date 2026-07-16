package parser.infix

import parser.ExpressionParser
import parser.Parser
import program.expression.AssignmentExpression
import program.expression.Expression

class AssignmentExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        val operator = parser.next()
        val right = ExpressionParser.parse(parser, precedence)
        return AssignmentExpression(operator.value, left, right)
    }
}
