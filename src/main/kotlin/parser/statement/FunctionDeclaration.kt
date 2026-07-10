package parser.statement

import parser.ParseException
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
        fun parse(program: Program): Statement {
            val parameters: MutableList<String> = ArrayList()
            val defaultParameters: MutableList<Pair<String, Expression>> = ArrayList()
            var parseDefaults = false

            program.expect(Type.KEYWORD, "fn")
            val name = program.expect(Type.IDENTIFIER)
            program.expect(Type.PARENTHESIS, "(")


            while (!program.peekIs(Type.PARENTHESIS, ")")) {
                val parameterName = program.expect(Type.IDENTIFIER)

                if (program.peekIs(Type.ASSIGN, "=")) {
                    parseDefaults = true
                }

                if (parseDefaults) {
                    try {
                        program.expect(Type.ASSIGN, "=")
                        defaultParameters.add(Pair(parameterName, Expression.parse(program)))
                    } catch (_: ParseException) {
                        throw ParseException("Function cannot have parameter with default after parameter without default")
                    }
                } else {
                    parameters.add(parameterName)
                }

                if (!program.peekIs(Type.PARENTHESIS, ")")) {
                    program.expect(Type.COMMA)
                }
            }

            program.expect(Type.PARENTHESIS, ")")
            program.expect(Type.CURLY_BRACE, "{")
            val statements = StatementList()

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(program))
            }

            program.expect(Type.CURLY_BRACE, "}")

            val functionDeclaration = FunctionDeclaration(name, Funct(parameters, defaultParameters, statements))
            program.addFunction(name, functionDeclaration)
            return functionDeclaration
        }
    }
}
