package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.BangOperator

class BangExpressionParser(val precedence: Int) : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.BANG)
        val expression = ExpressionParser.parse(parser, precedence)
        return BangOperator(expression)
    }
}
