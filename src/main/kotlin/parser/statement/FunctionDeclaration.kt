package parser.statement

import parser.ParseException
import parser.Parser
import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.Funct
import parser.expression.value.Value
import tokenizer.Type

data class FunctionDeclaration(val name: String, val function: Funct) : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    fun call(program: Program, arguments: Arguments): Value<*>? {
        return function.call(program, arguments)
    }

    companion object {
        fun parse(parser: Parser): Statement {
            val parameters: MutableList<String> = ArrayList()
            val defaultParameters: MutableList<Pair<String, Expression>> = ArrayList()
            var parseDefaults = false

            parser.expect(Type.KEYWORD, "fn")
            val name = parser.expect(Type.IDENTIFIER)
            parser.expect(Type.PARENTHESIS, "(")


            while (!parser.peekIs(Type.PARENTHESIS, ")")) {
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

                if (!parser.peekIs(Type.PARENTHESIS, ")")) {
                    parser.expect(Type.COMMA)
                }
            }

            parser.expect(Type.PARENTHESIS, ")")
            parser.expect(Type.CURLY_BRACE, "{")
            val statements = StatementList()

            while (!parser.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.CURLY_BRACE, "}")

            val functionDeclaration = FunctionDeclaration(name, Funct(parameters, defaultParameters, statements))
            parser.program.addFunction(name, functionDeclaration)
            return functionDeclaration
        }
    }
}
