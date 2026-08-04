package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.StringExpression

class StringExpressionParser : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
        val expression = ExpressionParser.parse(0, true)
        val stringExpressionPairs: MutableList<Pair<String, Expression>> = mutableListOf(token.value to expression)

        while (parser.peekIs(Type.QUOTE_MIDDLE)) {
            val string = parser.expect(Type.QUOTE_MIDDLE)
            stringExpressionPairs.add(string to ExpressionParser.parse(0, true))
        }

        val finalString = parser.expect(Type.QUOTE_END)
        return StringExpression(stringExpressionPairs, finalString)
    }
}
