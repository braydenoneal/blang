package parser.prefix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.PositiveOperator

class PositiveExpressionParser(override val precedence: Int) : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.PLUS)
        val expression = ExpressionParser.parse(parser, precedence)
        return PositiveOperator(expression)
    }
}
