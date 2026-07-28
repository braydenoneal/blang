package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.statement.IncompleteException

interface Callable {
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

    fun innerCall(program: Program, arguments: Arguments): Value<*> {
        throw RunException("Value cannot be called")
    }

    fun abort(program: Program, arguments: Arguments) {
        arguments.abort()
    }

    fun done(program: Program, arguments: Arguments) {
        arguments.done()
    }
}
