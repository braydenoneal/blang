package parser.expression.prefix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import program.expression.AssignExpression
import program.expression.Expression

class VariableExpressionParser : PrefixParser {
    override fun parse(parser: Parser, spanStart: Int, token: Token): Expression {
        val expression = ExpressionParser.parse(parser, 0)

        if (expression !is AssignExpression) {
            throw ParseException("Variable expression expected an assign expression", parser.spanFrom(spanStart))
        }

        if (expression.operator != "=") {
            throw ParseException("Variable expression can only create a variable", parser.spanFrom(spanStart))
        }

        expression.local = true
        return expression
    }
}
