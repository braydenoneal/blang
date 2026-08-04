package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression

object ParametersParser {
    context(parser: Parser)
    fun parse(isStatement: Boolean = true, hasParameters: Boolean = true): Pair<MutableList<String>, MutableList<Pair<String, Expression>>> {
        val parameters: MutableList<String> = mutableListOf()
        val defaultParameters: MutableList<Pair<String, Expression>> = mutableListOf()

        if (isStatement) {
            parser.expect(Type.LEFT_PARENTHESIS)
        }

        val endTokenType = if (isStatement) Type.RIGHT_PARENTHESIS else Type.COLON

        while (!parser.peekIs(endTokenType) && hasParameters) {
            val parameterName = parser.expect(Type.IDENTIFIER)

            if (parser.peekIs(Type.EQUALS)) {
                parser.expect(Type.EQUALS)
                defaultParameters.add(parameterName to ExpressionParser.parse())
            } else {
                parameters.add(parameterName)
            }

            if (!parser.peekIs(endTokenType)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(endTokenType)
        return parameters to defaultParameters
    }
}
