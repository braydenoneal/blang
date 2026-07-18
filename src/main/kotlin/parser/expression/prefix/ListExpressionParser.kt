package parser.expression.prefix

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.ListExpression

class ListExpressionParser : PrefixParser {
    override fun parse(parser: Parser, token: Token, skipNewline: Boolean): Expression {
        val expressions: MutableList<Expression> = mutableListOf()

        while (!parser.peekIs(Type.RIGHT_SQUARE_BRACE)) {
            expressions.add(ExpressionParser.parse(parser, 0, true))

            if (!parser.peekIs(Type.RIGHT_SQUARE_BRACE)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.RIGHT_SQUARE_BRACE)
        return ListExpression(expressions)
    }
}
