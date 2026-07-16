package parser

import parser.expression.infix.*
import parser.expression.prefix.*
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
        // Prefix
        register(Type.IDENTIFIER, VariableExpressionParser())
        register(Type.LEFT_PARENTHESIS, GroupExpressionParser())
        register(Type.LEFT_SQUARE_BRACE, ListExpressionParser())
        register(Type.LEFT_CURLY_BRACE, StructExpressionParser())
        register(Type.BOOLEAN, LiteralExpressionParser())
        register(Type.QUOTE, LiteralExpressionParser())
        register(Type.FLOAT, LiteralExpressionParser())
        register(Type.INTEGER, LiteralExpressionParser())
        register(Type.NULL, LiteralExpressionParser())
        register(Type.FN_KEYWORD, FunctionExpressionParser())
        register(Type.MINUS, NegativeExpressionParser(6))
        register(Type.PLUS, PositiveExpressionParser(6))
        register(Type.BANG, BangExpressionParser(6))
        // Infix
        register(Type.ASSIGN, AssignmentExpressionParser(1))
        register(Type.IF_KEYWORD, ConditionalExpressionParser(2))
        register(Type.MINUS, SubtractExpressionParser(3))
        register(Type.PLUS, AddExpressionParser(3))
        register(Type.AND, AndExpressionParser(3))
        register(Type.OR, OrExpressionParser(3))
        register(Type.ASTERISK, MultiplyExpressionParser(4))
        register(Type.DOUBLE_SLASH, FloorDivideExpressionParser(4))
        register(Type.SLASH, DivideExpressionParser(4))
        register(Type.PERCENT, ModuloExpressionParser(4))
        register(Type.COMPARISON_OPERATOR, ComparisonExpressionParser(4))
        register(Type.CARET, ExponentiateExpressionParser(5))
        register(Type.LEFT_SQUARE_BRACE, ListAccessExpressionParser(7))
        register(Type.DOT, MemberExpressionParser(7))
    }
}
