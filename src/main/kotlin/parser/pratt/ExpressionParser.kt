package parser.pratt

import parser.ParseException
import parser.Parser
import parser.expression.Expression
import parser.pratt.Parsers.infixParsers
import parser.pratt.Parsers.prefixParsers
import parser.pratt.infix.InfixParser
import tokenizer.Token
import tokenizer.Type

object ExpressionParser {
    fun parse(parser: Parser, precedence: Int, skipNewline: Boolean = false): Expression {
        val token = parser.peek(skipNewline)

        if (token.type == Type.NEWLINE) {
            parser.nextAllowNewline()
            return parse(parser, precedence, skipNewline)
        }

        val prefixParser = prefixParsers[token.type] ?: throw ParseException("Invalid prefix token")
        var left = prefixParser.parse(parser, skipNewline)

        while (precedence < getPrecedence(parser.peek(skipNewline))) {
            left = getInfixParser(parser.peek(skipNewline)).parse(parser, left)
        }

        return left
    }

    fun getInfixParser(token: Token): InfixParser {
        return infixParsers[token.type] ?: throw ParseException("Invalid infix token")
    }

    fun getPrecedence(token: Token): Int {
        val parser = infixParsers[token.type]

        if (parser != null) {
            return parser.precedence
        }

        return 0
    }
}

/*
PRECEDENCE:
(   -   0
)   -   0
and   -   1
or   -   1
==   -   2
!=   -   2
<=   -   2
>=   -   2
<   -   2
>   -   2
+   -   3
-   -   3
*   -   4
//   -   4
/   -   4
%   -   4
^   -   5
 */
