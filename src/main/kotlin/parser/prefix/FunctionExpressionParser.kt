package parser.prefix

import parser.Parser
import program.expression.Expression
import program.expression.value.FunctionValue

class FunctionExpressionParser(override val precedence: Int) : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        return FunctionValue.parse(parser)
    }
}
