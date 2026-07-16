package parser.expression

import parser.Parser
import parser.Program
import parser.expression.value.Value
import parser.pratt.ExpressionParser

interface Expression {
    fun evaluate(program: Program): Value<*>?

    companion object {
        fun parse(parser: Parser, skipNewline: Boolean = false): Expression {
            return ExpressionParser.parse(parser, 0, skipNewline)
        }
    }
}
