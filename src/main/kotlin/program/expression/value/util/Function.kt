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
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        if (scope == null) {
            scope = Scope(program.scopes.last())
        }

        val scope = scope!!

        if (arguments.hasSelf) {
            scope.setLocal("self", arguments.getAny("self"))
        }

        for (name in parameters) {
            if (name !in scope.variables) {
                scope.setLocal(name, arguments.getAny(name))
            }
        }

        for ((name, expression) in defaultParameters) {
            if (name !in scope.variables) {
                scope.setLocal(name, arguments.getAny(name, expression.evaluate()))
            }
        }

        if (arguments.namelessArguments.size + arguments.namedArguments.size - (if (arguments.hasSelf) 1 else 0) > parameters.size + defaultParameters.size) {
            throw RunException("Extra argument(s) provided", arguments.span)
        }

        running = true
        program.addScope(scope)

        var returnValue: Value<*> = Null.VALUE
        val statement = statements.runNext()

        if (statement is ReturnStatement) {
            returnValue = statement.returnValue()
        }

        return returnValue
    }

    context(program: Program, arguments: Arguments)
    override fun abort() {
        if (running) {
            program.endScope()
        } else {
            arguments.abort()
        }
    }

    context(program: Program, arguments: Arguments)
    override fun done() {
        scope = null
        running = false
        arguments.done()
        program.endScope()
    }

    override fun toString(): String {
        val strings = mutableListOf<String>()

        for (name in parameters) {
            strings.add(name)
        }

        for ((name, _) in defaultParameters) {
            strings.add(name)
        }

        return "(${strings.joinToString(", ")}) { $statements }"
    }
}
