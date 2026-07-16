package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.ArithmeticOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class SubtractExpressionParser : InfixParser {
    override val precedence = 3

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.MINUS)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("-", left, right)
    }
}
