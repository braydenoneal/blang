package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.Null
import program.expression.value.Value

data class WaitBuiltin(override val arguments: Arguments, var counter: Int = 0) : Builtin(arguments), Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = if (arguments.namelessArguments.isEmpty()) IntegerValue(1) else arguments.integerValue(program, "value", 0) ?: return null

        counter++

        if (counter > value.value) {
            counter = 0
            return Null.VALUE
        }

        program.waitUntilNextTick()
        return null
    }
}
