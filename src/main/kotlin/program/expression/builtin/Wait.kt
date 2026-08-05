package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Callable
import program.expression.value.IntegerValue
import program.expression.value.Value
import program.expression.value.get
import program.expression.value.util.Null
import program.statement.IncompleteException

object Wait : Callable {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        val value = get<IntegerValue>("value", IntegerValue(1))

        arguments.counter++

        if (arguments.counter > value.value) {
            return Null.VALUE
        }

        program.waitUntilNextTick()
        throw IncompleteException()
    }
}
