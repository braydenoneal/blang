package parser.expression

import parser.ParseException
import parser.Program
import parser.expression.value.StructValue
import parser.expression.value.Value
import tokenizer.Type

data class StructExpression(val expressions: List<Pair<String, Expression>>) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return StructValue(expressions.map { Pair(it.first, it.second.evaluate(program)) } as MutableList<Pair<String, Value<*>>>)
    }

    companion object {
        fun parse(program: Program): Expression {
            val expressions: MutableList<Pair<String, Expression>> = ArrayList()
            program.expect(Type.CURLY_BRACE, "{")

            while (!program.peekIs(Type.CURLY_BRACE, "}")) {
                val name = program.next()

                if (name.type != Type.IDENTIFIER) {
                    throw ParseException("Struct key is not an identifier")
                }

                program.expect(Type.COLON)
                expressions.add(Pair(name.value, Expression.parse(program)))

                if (!program.peekIs(Type.CURLY_BRACE, "}")) {
                    program.expect(Type.COMMA)
                }
            }

            program.expect(Type.CURLY_BRACE, "}")
            return StructExpression(expressions)
        }
    }
}
