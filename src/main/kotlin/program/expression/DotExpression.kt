package program.expression

import program.Program
import program.expression.value.StructValue
import program.expression.value.Value

data class DotExpression(val left: Expression, val right: String) : Expression {
    override fun evaluate(program: Program): Value<*> {
        val value = left.evaluate(program).cast<StructValue>()
        return value.get(right)
    }
}
