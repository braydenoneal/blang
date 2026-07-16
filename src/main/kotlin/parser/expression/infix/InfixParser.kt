package parser.expression.infix

import parser.Parser
import program.expression.Expression

interface InfixParser {
    val precedence: Int

    fun parse(parser: Parser, left: Expression): Expression
}
