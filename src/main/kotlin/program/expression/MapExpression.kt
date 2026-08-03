package program.expression

import program.Program
import program.expression.value.MapValue
import program.expression.value.Value

class MapExpression(
    val expressions: MutableList<Pair<Expression, Expression>>,
    val computed: MutableList<Pair<Value<*>, Value<*>>> = mutableListOf(),
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        val map = mutableMapOf<Value<*>, Value<*>>()

        for (i in expressions.indices) {
            if (computed.size > i) {
                map[computed[i].first] = computed[i].second
            } else {
                val key = expressions[i].first.evaluate(program)
                val value = expressions[i].second.evaluate(program)
                map[key] = value
                computed.add(key to value)
            }
        }

        return MapValue(map)
    }

    override fun done(program: Program) {
        computed.clear()
    }

    override fun toString(): String {
        return expressions.joinToString(", ")
    }
}
