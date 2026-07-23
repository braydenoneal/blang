package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression

interface PrefixParser {
    fun parse(parser: Parser, token: Token): Expression

    companion object {
        val prefixParsers: MutableMap<Type, PrefixParser> = mutableMapOf()

        fun register(type: Type, parser: PrefixParser) {
            prefixParsers[type] = parser
        }

        fun initialize() {
            register(Type.IDENTIFIER, IdentifierExpressionParser())
            register(Type.LEFT_PARENTHESIS, GroupExpressionParser())
            register(Type.LEFT_SQUARE_BRACE, ListExpressionParser())
            register(Type.LEFT_CURLY_BRACE, StructExpressionParser())
            register(Type.BOOLEAN, LiteralExpressionParser())
            register(Type.QUOTE, LiteralExpressionParser())
            register(Type.FLOAT, LiteralExpressionParser())
            register(Type.INTEGER, LiteralExpressionParser())
            register(Type.NULL, LiteralExpressionParser())
            register(Type.FN_KEYWORD, FunctionValueParser())
            register(Type.VAR_KEYWORD, VariableExpressionParser())
            register(Type.MINUS, UnaryExpressionParser(6))
            register(Type.PLUS, UnaryExpressionParser(6))
            register(Type.BANG, UnaryExpressionParser(6))
        }
    }
}
