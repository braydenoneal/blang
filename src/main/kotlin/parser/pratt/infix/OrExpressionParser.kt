package parser.pratt.infix

import parser.Parser
import parser.expression.Expression
import parser.expression.operator.BooleanOperator
import parser.pratt.ExpressionParser
import tokenizer.Type

class OrExpressionParser : InfixParser {
    override val precedence = 3

    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.OR)
        val right = ExpressionParser.parse(parser, precedence)
        return BooleanOperator("or", left, right)
    }
}
