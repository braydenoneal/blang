package program.statement

import parser.ParseException
import parser.Parser
import parser.StatementParser
import parser.tokenizer.Type
import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.Funct
import program.expression.value.Value

data class FunctionDeclaration(val name: String, val function: Funct) : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    fun call(program: Program, arguments: Arguments): Value<*>? {
        return function.call(program, arguments)
    }

    companion object {
        fun parse(parser: Parser): Statement {
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
                        defaultParameters.add(Pair(parameterName, Expression.parse(parser)))
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

            val functionDeclaration = FunctionDeclaration(name, Funct(parameters, defaultParameters, statements))
            parser.program.addFunction(name, functionDeclaration)
            return functionDeclaration
        }
    }
}
