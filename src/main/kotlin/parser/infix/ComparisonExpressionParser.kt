package parser.infix

import parser.ExpressionParser
import parser.Parser
import program.expression.Expression
import program.expression.operator.ComparisonOperator

class ComparisonExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        val operator = parser.next()
        val right = ExpressionParser.parse(parser, precedence)
        return ComparisonOperator(operator.value, left, right)
    }
}
