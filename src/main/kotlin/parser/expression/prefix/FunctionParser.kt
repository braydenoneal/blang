package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.statement.StatementParser
import parser.tokenizer.Type
import program.expression.value.util.Function
import program.statement.ReturnStatement
import program.statement.StatementList

object FunctionParser {
    fun parse(parser: Parser, isStatement: Boolean = true, hasParameters: Boolean = true): Function {
        val (parameters, defaultParameters) = ParametersParser.parse(parser, isStatement, hasParameters)
        val statements = StatementList()

        if (parser.peekIs(Type.LEFT_CURLY_BRACE)) {
            parser.next()

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(StatementParser.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)
        } else {
            statements.add(ReturnStatement(ExpressionParser.parse(parser)))
        }

        return Function(parameters, defaultParameters, statements)
    }
}
