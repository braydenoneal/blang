package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class SubtractExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.MINUS)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("-", left, right)
    }
}
