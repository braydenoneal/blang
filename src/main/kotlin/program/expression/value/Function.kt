package program.expression.value

import program.Program
import program.RunException
import program.Scope
import program.expression.Arguments
import program.expression.Callable
import program.expression.Expression
import program.statement.ReturnStatement
import program.statement.StatementList

data class Function(
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
