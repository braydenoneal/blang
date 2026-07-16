package program.expression

import parser.Parser
import parser.expression.ExpressionParser
import program.Program
import program.expression.value.Value

interface Expression {
    fun evaluate(program: Program): Value<*>?

    companion object {
        fun parse(parser: Parser, skipNewline: Boolean = false): Expression {
            return ExpressionParser.parse(parser, 0, skipNewline)
        }
    }
}
