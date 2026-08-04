package parser.statement

import parser.ParseException
import parser.Parser
import parser.expression.prefix.ParametersParser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.FunctionValue
import program.expression.value.util.StructDefinition
import program.statement.FunctionStatement
import program.statement.Statement
import program.statement.StaticStatement
import program.statement.StructStatement

class StructStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val name = parser.expect(Type.IDENTIFIER)
        val (parameters, defaultParameters) = ParametersParser.parse(isStatement = true, hasParameters = true)
        parser.expect(Type.LEFT_CURLY_BRACE)
        val functions: MutableMap<String, FunctionValue> = mutableMapOf()
        var staticFunctions: MutableMap<String, FunctionValue> = mutableMapOf()
        var staticVariables: MutableMap<String, Expression> = mutableMapOf()
        var parsedStatic = false

        while (!(parser.peekIs(Type.RIGHT_CURLY_BRACE))) {
            when (val statement = StatementParser.parse()) {
                is FunctionStatement -> functions[statement.name] = FunctionValue(statement.function)
                is StaticStatement -> {
                    if (parsedStatic) {
                        throw ParseException("Cannot define static more than once", parser.spanFrom(spanStart))
                    }

                    staticFunctions = statement.functions
                    staticVariables = statement.variables
                    parsedStatic = true
                }

                else -> throw ParseException("Statement ${statement::class} not allowed in a struct definition", parser.spanFrom(spanStart))
            }
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        val struct = StructDefinition(name, parameters, defaultParameters, functions, staticFunctions, staticVariables)
        parser.program.addStruct(name, struct)
        return StructStatement(name, struct)
    }
}
