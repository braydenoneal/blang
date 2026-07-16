package parser

import parser.Parsers.infixParsers
import parser.Parsers.prefixParsers
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression

object ExpressionParser {
    fun parse(parser: Parser, precedence: Int, skipNewline: Boolean = false): Expression {
        val token = parser.peek(skipNewline)

        if (token.type == Type.NEWLINE) {
            parser.nextAllowNewline()
        }

        val prefixParser = prefixParsers[token.type] ?: throw ParseException("Invalid prefix token")
        var left = prefixParser.parse(parser, skipNewline)

        while (precedence < getPrecedence(parser.peek(skipNewline))) {
            val token = parser.peek(skipNewline)

            if (token.type == Type.NEWLINE) {
                parser.nextAllowNewline()
            }

            val infixParser = infixParsers[token.type] ?: throw ParseException("Invalid infix token")
            left = infixParser.parse(parser, left)
        }

        return left
    }

    fun getPrecedence(token: Token): Int {
        val parser = infixParsers[token.type]

        if (parser != null) {
            return parser.precedence
        }

        return 0
    }
}
