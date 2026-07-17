package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.StructExpression

class StructExpressionParser : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val expressions: MutableList<Pair<String, Expression>> = mutableListOf()
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            val name = parser.next()

            if (name.type != Type.IDENTIFIER) {
                throw ParseException("Struct key is not an identifier")
            }

            parser.expect(Type.COLON)
            expressions.add(name.value to ExpressionParser.parse(parser))

            if (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        return StructExpression(expressions)
    }
}
