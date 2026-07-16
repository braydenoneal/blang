package parser.infix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class MultiplyExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.ASTERISK)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("*", left, right)
    }
}
