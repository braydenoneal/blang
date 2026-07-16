package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Expression

class GroupExpressionParser : PrefixParser {
    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        parser.expect(Type.LEFT_PARENTHESIS)
        val expression = ExpressionParser.parse(parser, 0, true)
        parser.expect(Type.RIGHT_PARENTHESIS)
        return expression
    }
}
