package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class AddExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.PLUS)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("+", left, right)
    }
}
