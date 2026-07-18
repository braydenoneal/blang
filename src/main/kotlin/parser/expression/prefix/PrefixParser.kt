package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression

interface PrefixParser {
    fun parse(parser: Parser, token: Token): Expression
}
