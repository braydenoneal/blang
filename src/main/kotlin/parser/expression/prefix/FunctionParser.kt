package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.statement.StatementParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.Function
import program.statement.ReturnStatement
import program.statement.StatementList

object FunctionParser {
    fun parse(parser: Parser, isStatement: Boolean = true): Function {
        val parameters: MutableList<String> = mutableListOf()
        val defaultParameters: MutableList<Pair<String, Expression>> = mutableListOf()
        var parseDefaults = false

        if (isStatement) {
            parser.expect(Type.LEFT_PARENTHESIS)
        }

        val endTokenType = if (isStatement) Type.RIGHT_PARENTHESIS else Type.COLON

        while (!parser.peekIs(endTokenType)) {
            val parameterName = parser.expect(Type.IDENTIFIER)

            if (parser.peekIs(Type.EQUALS)) {
                parseDefaults = true
            }

            if (parseDefaults) {
                try {
                    parser.expect(Type.EQUALS)
                    defaultParameters.add(parameterName to ExpressionParser.parse(parser))
                } catch (_: ParseException) {
                    throw ParseException("Function cannot have parameter with default after parameter without default")
                }
            } else {
                parameters.add(parameterName)
            }

            if (!parser.peekIs(endTokenType)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(endTokenType)
        val statements = StatementList()

        if (parser.peekIs(Type.LEFT_CURLY_BRACE)) {
            parser.next()

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(StatementParser.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)
        } else {
            statements.add(ReturnStatement(ExpressionParser.parse(parser)))
        }

        return Function(parameters, defaultParameters, statements)
    }
}
