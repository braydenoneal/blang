package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.statement.IncompleteException

interface Callable {
    context(program: Program, arguments: Arguments)
    fun call(): Value<*> {
        try {
            val value = innerCall()
            done()
            return value
        } catch (_: IncompleteException) {
            abort()
            throw IncompleteException()
        }
    }

    context(program: Program, arguments: Arguments)
    fun innerCall(): Value<*> {
        throw RunException("Value cannot be called", arguments.span)
    }

    context(program: Program, arguments: Arguments)
    fun abort() {
        arguments.abort()
    }

    context(program: Program, arguments: Arguments)
    fun done() {
        arguments.done()
    }

    context(program: Program, arguments: Arguments)
    fun callFunction(name: String, local: Boolean = true): Value<*> {
        try {
            val value = innerCallFunction(name, local)
            doneFunction()
            return value
        } catch (_: IncompleteException) {
            abortFunction()
            throw IncompleteException()
        }
    }

    context(program: Program, arguments: Arguments)
    fun innerCallFunction(name: String, local: Boolean = true): Value<*> {
        throw RunException("Value has no function '$name'", arguments.span)
    }

    context(program: Program, arguments: Arguments)
    fun abortFunction() {
        arguments.abort()
    }

    context(program: Program, arguments: Arguments)
    fun doneFunction() {
        arguments.done()
    }
}
