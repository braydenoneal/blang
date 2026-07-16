package parser.pratt.prefix

import parser.Parser
import parser.expression.Expression

interface PrefixParser {
    val precedence: Int

    fun parse(parser: Parser, skipNewline: Boolean = false): Expression
}
