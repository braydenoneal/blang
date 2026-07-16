package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.ComparisonOperator
import parser.pratt.ExpressionParser

class ComparisonExpressionParser : InfixParser {
    override val precedence = 4

    override fun parse(parser: Parser, left: Expression): Expression {
        val operator = parser.next()
        val right = ExpressionParser.parse(parser, precedence)
        return ComparisonOperator(operator.value, left, right)
    }
}
