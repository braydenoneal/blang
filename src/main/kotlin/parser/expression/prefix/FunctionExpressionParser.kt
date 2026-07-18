package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.statement.StatementParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.Funct
import program.expression.value.FunctionValue
import program.statement.ReturnStatement
import program.statement.StatementList

class FunctionExpressionParser : PrefixParser {
    override fun parse(parser: Parser, token: Token): Expression {
        val parameters: MutableList<String> = mutableListOf()
        val defaultParameters: MutableList<Pair<String, Expression>> = mutableListOf()
        var parseDefaults = false

        while (!parser.peekIs(Type.COLON)) {
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

            if (!parser.peekIs(Type.COLON)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.COLON)
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

        return FunctionValue(Funct(parameters, defaultParameters, statements))
    }
}
