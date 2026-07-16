package parser.prefix

import parser.Parser
import program.expression.Expression
import program.expression.value.FunctionValue

class FunctionExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        return FunctionValue.parse(parser)
    }
}
