package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.statement.StatementParser
import parser.tokenizer.Type
import program.expression.value.function.Function
import program.statement.ReturnStatement
import program.statement.StatementList

object FunctionParser {
    context(parser: Parser)
    fun parse(isStatement: Boolean = true, hasParameters: Boolean = true): Function {
        val (parameters, defaultParameters) = ParametersParser.parse(isStatement, hasParameters)
        val statements = StatementList()

        if (parser.peekIs(Type.LEFT_CURLY_BRACE)) {
            parser.next()

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(StatementParser.parse())
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)
        } else {
            statements.add(ReturnStatement(ExpressionParser.parse()))
        }

        return Function(parameters, defaultParameters, statements)
    }
}
