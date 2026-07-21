package program.expression

import program.Program
import program.expression.value.ListValue
import program.expression.value.Value

data class ListExpression(
    val expressions: MutableList<Expression>,
    val computed: MutableList<Value<*>> = mutableListOf(),
) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        val values: MutableList<Value<*>> = mutableListOf()

        for (i in expressions.indices) {
            if (computed.size > i) {
                values.add(computed[i])
            } else {
                val value = expressions[i].evaluate(program)
                values.add(value)
                computed.add(value)
            }
        }

        return ListValue(values)
    }

    override fun done(program: Program) {
        computed.clear()
    }
}
