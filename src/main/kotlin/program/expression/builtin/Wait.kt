package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.get
import program.expression.value.integer.IntegerValue
import program.expression.value.nullvalue.Null
import program.statement.IncompleteException

object Wait : Builtin() {
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
