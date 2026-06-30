package parser.expression.value

import parser.ParseException
import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.statement.ReturnStatement
import parser.statement.Statement
import tokenizer.Type

class FunctionValue(value: Funct) : Value<Funct>(value) {
    fun call(program: Program, arguments: Arguments): Value<*> {
        return value.call(program, arguments)
    }

    override fun toString(): String {
        return "fn" + value.parameters.toString() + ": " + value.statements.toString()
    }

    companion object {
        fun parse(program: Program): Expression {
            val parameters: MutableList<String> = ArrayList()
            val defaultParameters: MutableList<Pair<String, Expression>> = ArrayList()
            var parseDefaults = false

            program.expect(Type.KEYWORD, "fn")

            while (program.peek().type !== Type.COLON) {
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

                if (program.peek().type !== Type.COLON) {
                    program.expect(Type.COMMA)
                }
            }

            program.expect(Type.COLON)
            val statements: MutableList<Statement> = ArrayList()

            if (program.peekIs(Type.CURLY_BRACE, "{")) {
                program.next()

                while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                    statements.add(Statement.parse(program))
                }

                program.expect(Type.CURLY_BRACE, "}")
            } else {
                statements.add(ReturnStatement(Expression.parse(program)))
            }

            return FunctionValue(Funct(parameters, defaultParameters, statements))
        }
    }
}
