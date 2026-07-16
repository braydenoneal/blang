package parser.pratt.prefix

import parser.ParseException
import parser.Parser
import parser.expression.Expression
import parser.expression.value.*
import tokenizer.Type

class LiteralExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val token = parser.peek(skipNewline)

        return when (token.type) {
            Type.BOOLEAN -> BooleanValue(parser.next().value == "true")
            Type.QUOTE -> StringValue(parser.next().value)
            Type.FLOAT -> FloatValue(parser.next().value.toFloat())
            Type.INTEGER -> IntegerValue(parser.next().value.toInt())
            Type.NULL -> Null.parse(parser)
            else -> throw ParseException("Invalid literal")
        }
    }
}
