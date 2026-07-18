package parser.expression.prefix

import parser.Parser
import parser.tokenizer.Token
import program.expression.Expression
import program.expression.IdentifierExpression

class IdentifierExpressionParser : PrefixParser {
    override fun parse(parser: Parser, token: Token): Expression {
        return IdentifierExpression(token.value)
    }
}
