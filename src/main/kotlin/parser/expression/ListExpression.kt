package parser.expression

import parser.Program
import parser.expression.value.ListValue
import parser.expression.value.Value
import tokenizer.Type

data class ListExpression(val expressions: MutableList<Expression>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return ListValue(ListValue.toIndexValues(program, expressions) ?: return null)
    }

    companion object {
        fun parse(program: Program): Expression {
            val expressions: MutableList<Expression> = ArrayList()
            program.expect(Type.SQUARE_BRACE, "[")

            while (!program.peekIs(Type.SQUARE_BRACE, "]")) {
                expressions.add(Expression.parse(program))

                if (!program.peekIs(Type.SQUARE_BRACE, "]")) {
                    program.expect(Type.COMMA)
                }
            }

            program.expect(Type.SQUARE_BRACE, "]")
            return ListExpression(expressions)
        }
    }
}
