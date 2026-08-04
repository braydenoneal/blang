package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.GetExpression

class GetExpressionParser : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
        return GetExpression(FunctionParser.parse(isStatement = false, hasParameters = false))
    }
}
