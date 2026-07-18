package program.expression

import program.Program
import program.expression.value.StructValue
import program.expression.value.Value

data class StructExpression(val expressions: List<Pair<String, Expression>>) : Expression {
    override fun evaluate(program: Program): Value<*> {
        return StructValue(expressions.map { it.first to (it.second.evaluate(program)) } as MutableList<Pair<String, Value<*>>>)
    }
}
