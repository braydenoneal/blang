package parser.expression

import parser.ParseException
import parser.Parser
import parser.expression.infix.InfixParser
import parser.expression.infix.InfixParser.Companion.infixParsers
import parser.expression.prefix.PrefixParser
import parser.expression.prefix.PrefixParser.Companion.prefixParsers
import program.expression.Expression

object ExpressionParser {
    fun parse(parser: Parser, precedence: Int = 0, skipNewline: Boolean = false): Expression {
        val token = parser.next()
        val prefixParser = prefixParsers[token.type] ?: throw ParseException("Invalid prefix token")
        var left = prefixParser.parse(parser, token)

        while (precedence < nextPrecedence(parser, skipNewline)) {
            val token = parser.next()
            val infixParser = infixParsers[token.type] ?: throw ParseException("Invalid infix token")
            left = infixParser.parse(parser, token, left)
        }

        return left
    }

    fun nextPrecedence(parser: Parser, skipNewline: Boolean): Int {
        val token = if (skipNewline) parser.peek() else parser.peekAllowNewline()
        val parser = infixParsers[token.type]

        if (parser != null) {
            return parser.precedence
        }

        return 0
    }

    fun initialize() {
        PrefixParser.initialize()
        InfixParser.initialize()
    }
}
