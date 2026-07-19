package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.Null
import program.expression.value.Value
import program.statement.IncompleteException

data class WaitBuiltin(override val arguments: Arguments, var counter: Int = 0) : Builtin(arguments), Expression {
    override fun evaluate(program: Program): Value<*> {
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
