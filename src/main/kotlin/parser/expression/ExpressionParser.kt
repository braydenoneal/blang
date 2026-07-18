package parser.expression

import parser.ParseException
import parser.Parser
import parser.expression.infix.*
import parser.expression.prefix.*
import parser.tokenizer.Type
import program.expression.Expression

object ExpressionParser {
    val prefixParsers: MutableMap<Type, PrefixParser> = mutableMapOf()
    val infixParsers: MutableMap<Type, InfixParser> = mutableMapOf()

    fun register(type: Type, parser: PrefixParser) {
        prefixParsers[type] = parser
    }

    fun register(type: Type, parser: InfixParser) {
        infixParsers[type] = parser
    }

    fun initializePrefixParsers() {
        register(Type.IDENTIFIER, IdentifierExpressionParser())
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
    }

    fun initializeInfixParsers() {
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
        register(Type.LEFT_PARENTHESIS, CallExpressionParser(6))
        register(Type.LEFT_SQUARE_BRACE, ListAccessExpressionParser(7))
        register(Type.DOT, DotExpressionParser(7))
    }

    fun initialize() {
        initializePrefixParsers()
        initializeInfixParsers()
    }

    fun parse(parser: Parser, precedence: Int = 0, skipNewline: Boolean = false): Expression {
        val token = parser.peek(skipNewline)

        if (token.type == Type.NEWLINE) {
            parser.nextAllowNewline()
        }

        val prefixParser = prefixParsers[token.type] ?: throw ParseException("Invalid prefix token")
        var left = prefixParser.parse(parser, skipNewline)

        while (precedence < nextPrecedence(parser, skipNewline)) {
            val token = parser.peek(skipNewline)

            if (token.type == Type.NEWLINE) {
                parser.nextAllowNewline()
            }

            val infixParser = infixParsers[token.type] ?: throw ParseException("Invalid infix token")
            left = infixParser.parse(parser, left)
        }

        return left
    }

    fun nextPrecedence(parser: Parser, skipNewline: Boolean): Int {
        val token = parser.peek(skipNewline)
        val parser = infixParsers[token.type]

        if (parser != null) {
            return parser.precedence
        }

        return 0
    }
}
