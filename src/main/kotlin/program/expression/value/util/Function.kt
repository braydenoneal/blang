package program.expression.value.util

import program.Program
import program.RunException
import program.Scope
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.Callable
import program.expression.value.Value
import program.statement.ReturnStatement
import program.statement.StatementList

class Function(
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val statements: StatementList,
    var scope: Scope? = null,
    var running: Boolean = false,
) : Callable {
    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        if (scope == null) {
            scope = Scope(program.scopes.last())
        }

        val scope = scope!!

        if (arguments.hasSelf) {
            scope.setLocal("self", arguments.getAny(program, "self"))
        }

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

        if (arguments.namelessArguments.size + arguments.namedArguments.size - (if (arguments.hasSelf) 1 else 0) > parameters.size + defaultParameters.size) {
            throw RunException("Extra argument(s) provided")
        }

        running = true
        program.addScope(scope)

        var returnValue: Value<*> = Null.VALUE
        val statement = statements.runNext(program)

        if (statement is ReturnStatement) {
            returnValue = statement.returnValue(program)
        }

        return returnValue
    }

    override fun abort(program: Program, arguments: Arguments) {
        if (running) {
            program.endScope()
        } else {
            arguments.abort()
        }
    }

    override fun done(program: Program, arguments: Arguments) {
        scope = null
        running = false
        arguments.done()
        program.endScope()
    }
}
