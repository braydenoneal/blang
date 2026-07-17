package parser.expression

import parser.Parser
import parser.Parsers.builtins
import parser.statement.ArgumentsParser
import program.expression.CallExpression
import program.expression.Expression

object BuiltinExpressionParser {
    fun parse(parser: Parser, name: String): Expression {
        val arguments = ArgumentsParser.parse(parser)
        val builtin = builtins[name] ?: return CallExpression(name, arguments)
        return builtin.invoke(arguments)
    }
}
