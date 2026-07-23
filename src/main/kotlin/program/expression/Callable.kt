package program.expression

import program.Program
import program.expression.value.Value
import program.statement.IncompleteException

interface Callable {
    fun innerCall(program: Program, arguments: Arguments): Value<*>

    fun call(program: Program, arguments: Arguments): Value<*> {
        try {
            val value = innerCall(program, arguments)
            done(program, arguments)
            return value
        } catch (_: IncompleteException) {
            abort(program, arguments)
            throw IncompleteException()
        }
    }

    fun abort(program: Program, arguments: Arguments) {
        arguments.abort()
    }

    fun done(program: Program, arguments: Arguments) {
        arguments.done()
    }
}
