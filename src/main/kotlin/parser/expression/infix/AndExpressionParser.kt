package parser.expression.infix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.BooleanOperator

class AndExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        parser.expect(Type.AND)
        val right = ExpressionParser.parse(parser, precedence)
        return BooleanOperator("and", left, right)
    }
}
