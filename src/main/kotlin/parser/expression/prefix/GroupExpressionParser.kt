package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression

class GroupExpressionParser : PrefixParser {
    context(parser: Parser)
    override fun parse(spanStart: Int, token: Token): Expression {
        val expression = ExpressionParser.parse(0, true)
        parser.expect(Type.RIGHT_PARENTHESIS)
        return expression
    }
}
