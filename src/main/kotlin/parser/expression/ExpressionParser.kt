package parser.expression

import parser.ParseException
import parser.Parser
import parser.expression.infix.InfixParser
import parser.expression.infix.InfixParser.Companion.infixParsers
import parser.expression.prefix.PrefixParser
import parser.expression.prefix.PrefixParser.Companion.prefixParsers
import program.expression.Expression

object ExpressionParser {
    context(parser: Parser)
    fun parse(precedence: Int = 0, skipNewline: Boolean = false): Expression {
        val spanStart = parser.spanStart()
        val token = parser.next()
        val prefixParser = prefixParsers[token.type] ?: throw ParseException("Invalid prefix token '${token.value}'", parser.spanFrom(spanStart))
        var left = prefixParser.parse(spanStart, token).withSpan(spanStart, parser)

        while (precedence < nextPrecedence(skipNewline)) {
            val token = parser.next()
            val infixParser = infixParsers[token.type] ?: throw ParseException("Invalid infix token '${token.value}'", parser.spanFrom(spanStart))
            left = infixParser.parse(spanStart, token, left).withSpan(spanStart, parser)
        }

        return left
    }

    context(parser: Parser)
    fun nextPrecedence(skipNewline: Boolean): Int {
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
