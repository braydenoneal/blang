package program.expression

import program.Program
import program.expression.value.Value
import program.expression.value.map.MapValue

class MapExpression(
    val expressions: MutableList<Pair<Expression, Expression>>,
    val computed: MutableList<Pair<Value<*>, Value<*>>> = mutableListOf(),
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        val map = mutableMapOf<Value<*>, Value<*>>()

        for (i in expressions.indices) {
            if (computed.size > i) {
                map[computed[i].first] = computed[i].second
            } else {
                val key = expressions[i].first.evaluate()
                val value = expressions[i].second.evaluate()
                map[key] = value
                computed.add(key to value)
            }
        }

        return MapValue(map)
    }

    context(program: Program)
    override fun done() {
        computed.clear()
    }

    override fun toString(): String {
        return expressions.joinToString(", ")
    }
}
