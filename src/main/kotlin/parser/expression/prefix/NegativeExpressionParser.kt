package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.operator.NegativeOperator

class NegativeExpressionParser(val precedence: Int) : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.MINUS)
        val expression = ExpressionParser.parse(parser, precedence)
        return NegativeOperator(expression)
    }
}
