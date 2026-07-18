package parser.expression.infix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression

interface InfixParser {
    val precedence: Int

    fun parse(parser: Parser, token: Token, left: Expression): Expression
}
