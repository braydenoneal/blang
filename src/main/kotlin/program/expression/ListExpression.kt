package program.expression

import program.Program
import program.expression.value.ListValue
import program.expression.value.Value

data class ListExpression(val expressions: MutableList<Expression>) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        return ListValue(ListValue.toIndexValues(program, expressions) ?: return null)
    }
}
