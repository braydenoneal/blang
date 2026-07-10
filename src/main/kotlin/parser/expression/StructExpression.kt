package parser.expression

import parser.ParseException
import parser.Parser
import parser.Program
import parser.expression.value.StructValue
import parser.expression.value.Value
import tokenizer.Type

data class StructExpression(val expressions: List<Pair<String, Expression>>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return StructValue(expressions.map { Pair(it.first, it.second.evaluate(program) ?: return null) } as MutableList<Pair<String, Value<*>>>)
    }

    companion object {
        fun parse(parser: Parser): Expression {
            val expressions: MutableList<Pair<String, Expression>> = ArrayList()
            parser.expect(Type.CURLY_BRACE, "{")

            while (!parser.peekIs(Type.CURLY_BRACE, "}")) {
                val name = parser.next()

                if (name.type != Type.IDENTIFIER) {
                    throw ParseException("Struct key is not an identifier")
                }

                parser.expect(Type.COLON)
                expressions.add(Pair(name.value, Expression.parse(parser)))

                if (!parser.peekIs(Type.CURLY_BRACE, "}")) {
                    parser.expect(Type.COMMA)
                }
            }

            parser.expect(Type.CURLY_BRACE, "}")
            return StructExpression(expressions)
        }
    }
}
