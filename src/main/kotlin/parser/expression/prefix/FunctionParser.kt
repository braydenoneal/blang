package parser.expression.prefix

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

        if (isStatement) {
            parser.expect(Type.LEFT_PARENTHESIS)
        }

        val endTokenType = if (isStatement) Type.RIGHT_PARENTHESIS else Type.COLON

        while (!parser.peekIs(endTokenType)) {
            val parameterName = parser.expect(Type.IDENTIFIER)

            if (parser.peekIs(Type.EQUALS)) {
                parser.expect(Type.EQUALS)
                defaultParameters.add(parameterName to ExpressionParser.parse(parser))
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
