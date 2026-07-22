package program.expression.value

import program.Program
import program.RunException
import program.Scope
import program.expression.Arguments
import program.expression.Expression
import program.statement.IncompleteException
import program.statement.ReturnStatement
import program.statement.StatementList

data class Function(
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val statements: StatementList,
    var scope: Scope? = null,
    var running: Boolean = false,
    var arguments: Arguments? = null,
) {
    fun innerCall(program: Program, arguments: Arguments): Value<*> {
        if (this.arguments == null) {
            this.arguments = arguments
        }

        if (scope == null) {
            scope = Scope(program.scopes.last())
        }

        val scope = scope!!

        for (name in parameters) {
            if (name !in scope.variables) {
                scope.setLocal(name, arguments.getAny(program, name))
            }
        }

        for ((name, expression) in defaultParameters) {
            if (name !in scope.variables) {
                scope.setLocal(name, arguments.getAny(program, name, expression.evaluate(program)))
            }
        }

        if (arguments.namelessArguments.size + arguments.namedArguments.size > parameters.size + defaultParameters.size) {
            throw RunException("Extra argument(s) provided")
        }

        this.arguments?.done()
        this.arguments = null
        running = true
        program.addScope(scope)

        var returnValue: Value<*> = Null.VALUE
        val statement = statements.runNext(program)

        if (statement is ReturnStatement) {
            returnValue = statement.returnValue(program)
        }

        return returnValue
    }

    fun call(program: Program, arguments: Arguments): Value<*> {
        try {
            val value = innerCall(program, arguments)
            done(program)
            return value
        } catch (_: IncompleteException) {
            abort(program)
            throw IncompleteException()
        }
    }

    fun abort(program: Program) {
        if (running) {
            program.endScope()
        } else {
            arguments?.abort()
        }
    }

    fun done(program: Program) {
        scope = null
        running = false
        program.endScope()
    }
}
