package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression
import parser.expression.value.FunctionValue

class FunctionExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        return FunctionValue.parse(parser)
    }
}
