package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.booleanvalue.BooleanValue
import program.expression.value.floatvalue.FloatValue
import program.expression.value.integer.IntegerValue
import program.expression.value.nullvalue.Null
import program.expression.value.string.StringValue

class LiteralExpressionParser : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
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
