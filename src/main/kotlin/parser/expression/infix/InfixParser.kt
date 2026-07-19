package parser.expression.infix

import parser.Parser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression

interface InfixParser {
    val precedence: Int

    fun parse(parser: Parser, token: Token, left: Expression): Expression

    companion object {
        val infixParsers: MutableMap<Type, InfixParser> = mutableMapOf()

        fun register(type: Type, parser: InfixParser) {
            infixParsers[type] = parser
        }

        fun initialize() {
            register(Type.EQUALS, AssignExpressionParser(1))
            register(Type.MINUS_EQUALS, AssignExpressionParser(1))
            register(Type.PLUS_EQUALS, AssignExpressionParser(1))
            register(Type.IF_KEYWORD, ConditionalExpressionParser(2))
            register(Type.MINUS, BinaryOperatorExpressionParser(3))
            register(Type.PLUS, BinaryOperatorExpressionParser(3))
            register(Type.AND, BinaryOperatorExpressionParser(3))
            register(Type.OR, BinaryOperatorExpressionParser(3))
            register(Type.ASTERISK, BinaryOperatorExpressionParser(4))
            register(Type.DOUBLE_SLASH, BinaryOperatorExpressionParser(4))
            register(Type.SLASH, BinaryOperatorExpressionParser(4))
            register(Type.PERCENT, BinaryOperatorExpressionParser(4))
            register(Type.COMPARISON_OPERATOR, BinaryOperatorExpressionParser(4))
            register(Type.CARET, BinaryOperatorExpressionParser(5))
            register(Type.LEFT_PARENTHESIS, CallExpressionParser(6))
            register(Type.LEFT_SQUARE_BRACE, AccessExpressionParser(7))
            register(Type.DOT, DotExpressionParser(7))
        }
    }
}
