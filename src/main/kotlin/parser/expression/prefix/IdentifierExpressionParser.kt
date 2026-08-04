package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.IdentifierExpression

class IdentifierExpressionParser : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
        return IdentifierExpression(token.value)
    }
}
