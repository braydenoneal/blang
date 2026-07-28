package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.statement.Statement
import program.statement.StaticVariableStatement

class StaticVariableStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val name = parser.expect(Type.IDENTIFIER)
        parser.expect(Type.EQUALS)
        val expression = ExpressionParser.parse(parser)
        return StaticVariableStatement(name, expression)
    }
}
