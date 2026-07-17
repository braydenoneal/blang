package program.expression

import program.Program
import program.RunException
import program.expression.value.ListValue
import program.expression.value.Value

data class ListAccessExpression(
    val listExpression: Expression,
    val indexExpression: Expression,
) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val list = listExpression.evaluate(program) ?: return null
        val index = indexExpression.evaluate(program) ?: return null

        if (list !is ListValue) {
            throw RunException("Expression is not a list")
        }

        return list.get(index)
    }
}
