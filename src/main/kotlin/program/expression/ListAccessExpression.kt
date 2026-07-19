package program.expression

import program.Program
import program.expression.value.ListValue
import program.expression.value.Value

data class ListAccessExpression(
    val listExpression: Expression,
    val indexExpression: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val list = listExpression.evaluate(program).cast<ListValue>()
        val index = indexExpression.evaluate(program)
        return list.get(index)
    }
}
