package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.IntegerValue
import parser.expression.value.Null
import parser.expression.value.Value


data class WaitBuiltin(val arguments: Arguments, var counter: Int = 0) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = if (arguments.arguments.isEmpty()) IntegerValue(1) else this.arguments.integerValue(program, "value", 0) ?: return null

        counter++

        if (counter > value.value) {
            counter = 0
            return Null.VALUE
        }

        program.waitUntilNextTick()
        return null
    }
}
