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
) {
    fun innerCall(program: Program, arguments: Arguments): Value<*> {
        if (scope == null) {
            scope = Scope(program.scopes.last())
        }

        val scope = scope!!

        arguments.namedArguments.forEach { (name: String, expression: Expression) ->
            val hasDefault = defaultParameters.stream().anyMatch { it.first == name }

            if (parameters.contains(name) || hasDefault) {
                scope.setLocal(name, expression.evaluate(program))
            } else {
                throw RunException("Provided extra argument '$name'")
            }
        }

        for (i in parameters.indices) {
            if (scope.getLocal(parameters[i]) == null) {
                if (arguments.namelessArguments.size > i) {
                    scope.setLocal(
                        parameters[i],
                        arguments.namelessArguments[i].evaluate(program),
                    )
                } else {
                    throw RunException("Missing argument '" + parameters[i] + "'")
                }
            }
        }

        for (i in parameters.size..<arguments.namelessArguments.size) {
            if (defaultParameters.size > i - parameters.size) {
                scope.setLocal(
                    defaultParameters[i - parameters.size].first,
                    arguments.namelessArguments[i].evaluate(program),
                )
            } else {
                throw RunException("Provided extra argument")
            }
        }

        for (parameter in defaultParameters) {
            if (scope.getLocal(parameter.first) == null) {
                scope.setLocal(parameter.first, parameter.second.evaluate(program))
            }
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
        }
    }

    fun done(program: Program) {
        scope = null
        running = false
        program.endScope()
    }
}
