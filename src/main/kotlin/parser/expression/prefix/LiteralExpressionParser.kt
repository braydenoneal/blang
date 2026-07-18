package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.*

class LiteralExpressionParser : PrefixParser {
    override fun parse(parser: Parser, token: Token, skipNewline: Boolean): Expression {
        return when (token.type) {
            Type.BOOLEAN -> BooleanValue(token.value == "true")
            Type.QUOTE -> StringValue(token.value)
            Type.FLOAT -> FloatValue(token.value.toFloat())
            Type.INTEGER -> IntegerValue(token.value.toInt())
            Type.NULL -> Null.VALUE
            else -> throw ParseException("Invalid literal")
        }
    }
}
