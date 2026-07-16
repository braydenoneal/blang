package parser.infix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class DivideExpressionParser : InfixParser {
    override val precedence = 4

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.SLASH)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("/", left, right)
    }
}
