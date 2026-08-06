package parser.statement

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.value.function.FunctionValue
import program.statement.FunctionStatement
import program.statement.Statement
import program.statement.StaticStatement
import program.statement.StaticVariableStatement

class StaticStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        parser.expect(Type.LEFT_CURLY_BRACE)
        val functions: MutableMap<String, FunctionValue> = mutableMapOf()
        val variables: MutableMap<String, Expression> = mutableMapOf()

        while (!(parser.peekIs(Type.RIGHT_CURLY_BRACE))) {
            when (val statement = StatementParser.parse()) {
                is FunctionStatement -> functions[statement.name] = FunctionValue(statement.function)
                is StaticVariableStatement -> variables[statement.name] = statement.expression
                else -> throw ParseException("Statement ${statement::class} not allowed in a static statement", parser.spanFrom(spanStart))
            }
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)
        return StaticStatement(functions, variables)
    }
}
