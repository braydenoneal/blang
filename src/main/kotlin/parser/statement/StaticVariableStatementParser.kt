package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.statement.Statement
import program.statement.StaticVariableStatement

class StaticVariableStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val name = parser.expect(Type.IDENTIFIER)
        parser.expect(Type.EQUALS)
        val expression = ExpressionParser.parse()
        return StaticVariableStatement(name, expression)
    }
}
