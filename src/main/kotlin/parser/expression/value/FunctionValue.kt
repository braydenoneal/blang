package parser.expression.value

import parser.ParseException
import parser.Parser
import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.statement.ReturnStatement
import parser.statement.Statement
import parser.statement.StatementList
import tokenizer.Type

class FunctionValue(value: Funct) : Value<Funct>(value) {
    fun call(program: Program, arguments: Arguments): Value<*>? {
        return value.call(program, arguments)
    }

    override fun toString(): String {
        return "fn" + value.parameters.toString() + ": " + value.statements.toString()
    }

    companion object {
        fun parse(parser: Parser): Expression {
            val parameters: MutableList<String> = ArrayList()
            val defaultParameters: MutableList<Pair<String, Expression>> = ArrayList()
            var parseDefaults = false

            parser.expect(Type.KEYWORD, "fn")

            while (parser.peek().type !== Type.COLON) {
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

                if (parser.peek().type !== Type.COLON) {
                    parser.expect(Type.COMMA)
                }
            }

            parser.expect(Type.COLON)
            val statements = StatementList()

            if (parser.peekIs(Type.CURLY_BRACE, "{")) {
                parser.next()

                while (!parser.peekIs(Type.CURLY_BRACE, "}")) {
                    statements.add(Statement.parse(parser))
                }

                parser.expect(Type.CURLY_BRACE, "}")
            } else {
                statements.add(ReturnStatement(Expression.parse(parser)))
            }

            return FunctionValue(Funct(parameters, defaultParameters, statements))
        }
    }
}
