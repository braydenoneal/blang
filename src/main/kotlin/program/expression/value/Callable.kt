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
        throw RunException("Value cannot be called", arguments.span)
    }

    fun abort(program: Program, arguments: Arguments) {
        arguments.abort()
    }

    fun done(program: Program, arguments: Arguments) {
        arguments.done()
    }

    fun callFunction(program: Program, arguments: Arguments, name: String, local: Boolean = true): Value<*> {
        try {
            val value = innerCallFunction(program, arguments, name, local)
            doneFunction(program, arguments)
            return value
        } catch (_: IncompleteException) {
            abortFunction(program, arguments)
            throw IncompleteException()
        }
    }

    fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean = true): Value<*> {
        throw RunException("Value has no function '$name'", arguments.span)
    }

    fun abortFunction(program: Program, arguments: Arguments) {
        arguments.abort()
    }

    fun doneFunction(program: Program, arguments: Arguments) {
        arguments.done()
    }
}
