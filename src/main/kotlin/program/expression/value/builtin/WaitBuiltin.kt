package program.expression.value.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.IntegerValue
import program.expression.value.Null
import program.expression.value.Value
import program.statement.IncompleteException

class WaitBuiltin(var counter: Int = 0) : Builtin {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.get<IntegerValue>(program, "value", IntegerValue(1))

        counter++

        if (counter > value.value) {
            counter = 0
            return Null.VALUE
        }

        program.waitUntilNextTick()
        throw IncompleteException()
    }
}
