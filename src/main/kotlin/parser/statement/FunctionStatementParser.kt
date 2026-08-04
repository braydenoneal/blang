package parser.statement

import parser.Parser
import parser.expression.prefix.FunctionParser
import parser.tokenizer.Type
import program.expression.value.FunctionValue
import program.statement.FunctionStatement
import program.statement.Statement

class FunctionStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val name = parser.expect(Type.IDENTIFIER)
        val function = FunctionParser.parse()
        parser.program.addFunction(name, FunctionValue(function))
        return FunctionStatement(name, function)
    }
}
