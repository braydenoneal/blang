package parser.statement

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.FunctionValue
import program.statement.FunctionStatement
import program.statement.Statement
import program.statement.StaticStatements
import program.statement.StaticVariableStatement

class StaticStatementsParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        parser.expect(Type.LEFT_CURLY_BRACE)
        val functions: MutableMap<String, FunctionValue> = mutableMapOf()
        val variables: MutableMap<String, Expression> = mutableMapOf()

        while (!(parser.peekIs(Type.RIGHT_CURLY_BRACE))) {
            when (val statement = StaticStatementsParser.parse(parser)) {
                is FunctionStatement -> functions[statement.name] = FunctionValue(statement.function)
                is StaticVariableStatement -> variables[statement.name] = statement.expression
            }
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        return StaticStatements(functions, variables)
    }

    companion object {
        val statementParsers: MutableMap<Type, StatementParser> = mutableMapOf()

        fun register(type: Type, parser: StatementParser) {
            statementParsers[type] = parser
        }

        fun initialize() {
            register(Type.FN_KEYWORD, FunctionStatementParser())
            register(Type.VAR_KEYWORD, StaticVariableStatementParser())
        }

        fun parse(parser: Parser): Statement {
            val token = parser.next()
            val statementParser = statementParsers[token.type] ?: throw ParseException("Unrecognized token for static statements ${token.type}")
            return statementParser.parse(parser)
        }
    }
}
