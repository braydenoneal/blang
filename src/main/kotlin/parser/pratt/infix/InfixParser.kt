package parser.pratt.infix

import parser.Parser
import parser.expression.Expression

interface InfixParser {
    val precedence: Int

    fun parse(parser: Parser, left: Expression): Expression
}
