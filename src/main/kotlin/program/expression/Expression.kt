package program.expression

import program.Program
import program.expression.value.Value

interface Expression {
    fun evaluate(program: Program): Value<*>
}
