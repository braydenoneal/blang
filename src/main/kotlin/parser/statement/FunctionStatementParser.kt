package parser.statement

import parser.Parser
import parser.expression.prefix.FunctionParser
import parser.tokenizer.Type
import program.statement.FunctionStatement
import program.statement.Statement

class FunctionStatementParser : StatementParser {
    override fun parse(parser: Parser): Statement {
        val name = parser.expect(Type.IDENTIFIER)
        val functionStatement = FunctionStatement(FunctionParser.parse(parser))
        parser.program.addFunction(name, functionStatement)
        return functionStatement
    }
}
