package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.GetExpression

class GetExpressionParser : PrefixParser {
    override fun parse(parser: Parser, spanStart: Int, token: Token): Expression {
        return GetExpression(FunctionParser.parse(parser, isStatement = false, hasParameters = false))
    }
}
