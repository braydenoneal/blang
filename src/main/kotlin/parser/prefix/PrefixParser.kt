package parser.prefix

import parser.Parser
import program.expression.Expression

interface PrefixParser {
    val precedence: Int

    fun parse(parser: Parser, skipNewline: Boolean = false): Expression
}
