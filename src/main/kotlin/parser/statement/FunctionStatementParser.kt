package parser.statement

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.Funct
import program.statement.FunctionStatement
import program.statement.Statement
import program.statement.StatementList

class FunctionStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val parameters: MutableList<String> = mutableListOf()
        val defaultParameters: MutableList<Pair<String, Expression>> = mutableListOf()
        var parseDefaults = false

        parser.expect(Type.FN_KEYWORD)
        val name = parser.expect(Type.IDENTIFIER)
        parser.expect(Type.LEFT_PARENTHESIS)


        while (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
            val parameterName = parser.expect(Type.IDENTIFIER)

            if (parser.peekIs(Type.ASSIGN, "=")) {
                parseDefaults = true
            }

            if (parseDefaults) {
                try {
                    parser.expect(Type.ASSIGN, "=")
                    defaultParameters.add(parameterName to ExpressionParser.parse(parser))
                } catch (_: ParseException) {
                    throw ParseException("Function cannot have parameter with default after parameter without default")
                }
            } else {
                parameters.add(parameterName)
            }

            if (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.RIGHT_PARENTHESIS)
        parser.expect(Type.LEFT_CURLY_BRACE)
        val statements = StatementList()

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            statements.add(StatementParser.parse(parser))
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)

        val functionStatement = FunctionStatement(name, Funct(parameters, defaultParameters, statements))
        parser.program.addFunction(name, functionStatement)
        return functionStatement
    }
}
