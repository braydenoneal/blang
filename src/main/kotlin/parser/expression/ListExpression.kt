package parser.expression

import parser.Parser
import parser.Program
import parser.expression.value.ListValue
import parser.expression.value.Value
import tokenizer.Type

data class ListExpression(val expressions: MutableList<Expression>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return ListValue(ListValue.toIndexValues(program, expressions) ?: return null)
    }

    companion object {
        fun parse(parser: Parser): Expression {
            val expressions: MutableList<Expression> = ArrayList()
            parser.expect(Type.SQUARE_BRACE, "[")

            while (!parser.peekIs(Type.SQUARE_BRACE, "]")) {
                expressions.add(Expression.parse(parser))

                if (!parser.peekIs(Type.SQUARE_BRACE, "]")) {
                    parser.expect(Type.COMMA)
                }
            }

            parser.expect(Type.SQUARE_BRACE, "]")
            return ListExpression(expressions)
        }
    }
}
