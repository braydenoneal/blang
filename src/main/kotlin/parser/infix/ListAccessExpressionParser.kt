package parser.infix

import parser.ExpressionParser
import parser.Parser
import parser.tokenizer.Type
import program.expression.Expression
import program.expression.ListAccessExpression

class ListAccessExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, left: Expression): Expression {
        val indices: MutableList<Expression> = mutableListOf()

        // TODO: Parse one x[...] at a time and allow ListAccessExpression to handle its left being another ListAccessExpression for nested lists
        while (parser.peekIs(Type.LEFT_SQUARE_BRACE)) {
            parser.expect(Type.LEFT_SQUARE_BRACE)
            indices.add(ExpressionParser.parse(parser, precedence, true))
            parser.expect(Type.RIGHT_SQUARE_BRACE)
        }

        return ListAccessExpression(left, indices)
    }
}
