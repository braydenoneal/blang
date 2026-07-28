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
import program.statement.StaticStatements
import program.statement.StructStatement

class StructStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val name = parser.expect(Type.IDENTIFIER)
        val (parameters, defaultParameters) = ParametersParser.parse(parser, isStatement = true, hasParameters = true)
        parser.expect(Type.LEFT_CURLY_BRACE)
        val functions: MutableMap<String, FunctionValue> = mutableMapOf()
        var staticFunctions: MutableMap<String, FunctionValue> = mutableMapOf()
        var staticVariables: MutableMap<String, Expression> = mutableMapOf()
        var parsedStatic = false

        while (!(parser.peekIs(Type.RIGHT_CURLY_BRACE))) {
            when (val statement = StructStatementParser.parse(parser)) {
                is FunctionStatement -> functions[statement.name] = FunctionValue(statement.function)
                is StaticStatements -> {
                    if (!parsedStatic) {
                        staticFunctions = statement.functions
                        staticVariables = statement.variables
                    } else {
                        throw ParseException("Cannot define static more than once")
                    }

                    parsedStatic = true
                }
            }
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        val struct = StructDefinition(parameters, defaultParameters, functions, staticFunctions, staticVariables)
        parser.program.addStruct(name, struct)
        return StructStatement(name, struct)
    }

    companion object {
        val statementParsers: MutableMap<Type, StatementParser> = mutableMapOf()

        fun register(type: Type, parser: StatementParser) {
            statementParsers[type] = parser
        }

        fun initialize() {
            register(Type.FN_KEYWORD, FunctionStatementParser())
            register(Type.STATIC_KEYWORD, StaticStatementsParser())
        }

        fun parse(parser: Parser): Statement {
            val token = parser.next()
            val statementParser = statementParsers[token.type] ?: throw ParseException("Unrecognized token for struct definition ${token.type}")
            return statementParser.parse(parser)
        }
    }
}
