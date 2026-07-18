package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.operator.ArithmeticOperator

class DivideExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val right = ExpressionParser.parse(parser, precedence)
        return ArithmeticOperator("/", left, right)
    }
}
