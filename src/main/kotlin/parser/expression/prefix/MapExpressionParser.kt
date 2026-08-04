package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.MapExpression

class MapExpressionParser : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
        val map = mutableListOf<Pair<Expression, Expression>>()

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            val key = ExpressionParser.parse(0, true)
            parser.expect(Type.COLON)
            val value = ExpressionParser.parse(0, true)
            map.add(key to value)

            if (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                parser.expect(Type.COMMA)
            } else if (parser.peekIs(Type.COMMA)) {
                parser.next()
            }
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        return MapExpression(map)
    }
}
