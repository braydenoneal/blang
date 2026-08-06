package program.expression

import program.Program
import program.expression.value.Value
import program.expression.value.list.ListValue

class ListExpression(
    val expressions: MutableList<Expression>,
    val computed: MutableList<Value<*>> = mutableListOf(),
) : Expression() {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        val values: MutableList<Value<*>> = mutableListOf()

        for (i in expressions.indices) {
            if (computed.size > i) {
                values.add(computed[i])
            } else {
                val value = expressions[i].evaluate()
                values.add(value)
                computed.add(value)
            }
        }

        return ListValue(values)
    }

    context(program: Program)
    override fun done() {
        computed.clear()
    }

    override fun toString(): String {
        return expressions.joinToString(", ")
    }
}
