package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.ArithmeticOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class ExponentiateExpressionParser : InfixParser {
    override val precedence = 5

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.CARET)
        val right = ExpressionParser.parse(parser, precedence - 1)
        return ArithmeticOperator("^", left, right)
    }
}
