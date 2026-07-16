package parser.prefix

import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.ListExpression

class ListExpressionParser : PrefixParser {
    override val precedence = 0

    override fun parse(parser: Parser, skipNewline: Boolean): Expression {
        val expressions: MutableList<Expression> = mutableListOf()
        parser.expect(Type.LEFT_SQUARE_BRACE)

        while (!parser.peekIs(Type.RIGHT_SQUARE_BRACE)) {
            expressions.add(Expression.parse(parser, true))

            if (!parser.peekIs(Type.RIGHT_SQUARE_BRACE)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.RIGHT_SQUARE_BRACE)
        return ListExpression(expressions)
    }
}
