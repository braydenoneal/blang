package program.expression

import program.Program
import program.expression.value.ListValue
import program.expression.value.Value

data class ListExpression(val expressions: MutableList<Expression>) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val values: MutableList<Value<*>> = mutableListOf()

        for (expression in expressions) {
            values.add(expression.evaluate(program))
        }

        return ListValue(values)
    }
}
