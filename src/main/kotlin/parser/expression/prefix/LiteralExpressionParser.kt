package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.*

class LiteralExpressionParser : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val token = parser.peek(skipNewline)

        return when (token.type) {
            Type.BOOLEAN -> BooleanValue(parser.next().value == "true")
            Type.QUOTE -> StringValue(parser.next().value)
            Type.FLOAT -> FloatValue(parser.next().value.toFloat())
            Type.INTEGER -> IntegerValue(parser.next().value.toInt())
            Type.NULL -> {
                parser.next()
                Null.VALUE
            }

            else -> throw ParseException("Invalid literal")
        }
    }
}
