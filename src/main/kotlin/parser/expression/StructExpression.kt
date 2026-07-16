package parser.expression

import parser.Program
import parser.expression.value.StructValue
import parser.expression.value.Value

data class StructExpression(val expressions: List<Pair<String, Expression>>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return StructValue(expressions.map { Pair(it.first, it.second.evaluate(program) ?: return null) } as MutableList<Pair<String, Value<*>>>)
    }
}
