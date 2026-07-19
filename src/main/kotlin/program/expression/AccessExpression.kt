package program.expression

import program.Program
import program.expression.value.ListValue
import program.expression.value.Value

data class AccessExpression(
    val left: Expression,
    val right: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val list = left.evaluate(program).cast<ListValue>()
        val index = right.evaluate(program)
        return list.get(index)
    }
}
