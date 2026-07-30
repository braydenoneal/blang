package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.StringValue
import program.expression.value.util.Null

class LiteralExpressionParser : PrefixParser {
    override fun parse(parser: Parser, spanStart: Int, token: Token): Expression {
        return when (token.type) {
            Type.BOOLEAN -> BooleanValue(token.value == "true")
            Type.QUOTE -> StringValue(token.value)
            Type.FLOAT -> FloatValue(token.value.toFloat())
            Type.INTEGER -> IntegerValue(token.value.toInt())
            Type.NULL -> Null.VALUE
            else -> throw ParseException("Invalid literal", parser.spanFrom(spanStart))
        }
    }
}
