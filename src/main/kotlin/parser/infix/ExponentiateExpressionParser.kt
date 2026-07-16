package parser.infix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class ExponentiateExpressionParser : InfixParser {
    override val precedence = 5

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.CARET)
        val right = ExpressionParser.parse(parser, precedence - 1)
        return ArithmeticOperator("^", left, right)
    }
}
