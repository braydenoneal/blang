package parser

import parser.infix.*
import parser.prefix.*
import parser.tokenizer.Type

object Parsers {
    val prefixParsers: MutableMap<Type, PrefixParser> = mutableMapOf()
    val infixParsers: MutableMap<Type, InfixParser> = mutableMapOf()

    fun register(type: Type, prefixParser: PrefixParser) {
        prefixParsers[type] = prefixParser
    }

    fun register(type: Type, infixParser: InfixParser) {
        infixParsers[type] = infixParser
    }

    fun initialize() {
        register(Type.IDENTIFIER, VariableExpressionParser())
        register(Type.LEFT_PARENTHESIS, GroupExpressionParser())
        register(Type.LEFT_SQUARE_BRACE, ListExpressionParser())
        register(Type.LEFT_CURLY_BRACE, StructExpressionParser())
        register(Type.MINUS, NegativeExpressionParser())
        register(Type.PLUS, PositiveExpressionParser())
        register(Type.BANG, BangExpressionParser())
        register(Type.FN_KEYWORD, FunctionExpressionParser())
        register(Type.BOOLEAN, LiteralExpressionParser())
        register(Type.QUOTE, LiteralExpressionParser())
        register(Type.FLOAT, LiteralExpressionParser())
        register(Type.INTEGER, LiteralExpressionParser())
        register(Type.NULL, LiteralExpressionParser())
        register(Type.MINUS, SubtractExpressionParser())
        register(Type.PLUS, AddExpressionParser())
        register(Type.ASTERISK, MultiplyExpressionParser())
        register(Type.DOUBLE_SLASH, FloorDivideExpressionParser())
        register(Type.SLASH, DivideExpressionParser())
        register(Type.PERCENT, ModuloExpressionParser())
        register(Type.CARET, ExponentiateExpressionParser())
        register(Type.AND, AndExpressionParser())
        register(Type.OR, OrExpressionParser())
        register(Type.COMPARISON_OPERATOR, ComparisonExpressionParser())
        register(Type.LEFT_SQUARE_BRACE, ListAccessExpressionParser())
        register(Type.ASSIGN, AssignmentExpressionParser())
        register(Type.DOT, MemberExpressionParser())
        register(Type.IF_KEYWORD, ConditionalExpressionParser())
    }
}