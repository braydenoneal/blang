package parser.pratt

import parser.ParseException
import parser.Parser
import parser.expression.Expression
import parser.pratt.Parsers.infixParsers
import parser.pratt.Parsers.prefixParsers
import tokenizer.Token
import tokenizer.Type

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
