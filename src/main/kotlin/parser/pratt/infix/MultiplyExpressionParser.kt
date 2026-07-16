package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.ArithmeticOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class MultiplyExpressionParser : InfixParser {
    override val precedence = 4

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.ASTERISK)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("*", left, right)
    }
}
