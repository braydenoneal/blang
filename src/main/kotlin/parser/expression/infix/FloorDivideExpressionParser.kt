package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class FloorDivideExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.DOUBLE_SLASH)
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("//", left, right)
    }
}

