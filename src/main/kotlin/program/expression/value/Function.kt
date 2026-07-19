package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.Expression
import program.statement.ReturnStatement
import program.statement.StatementList

data class Function(
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val statements: StatementList,
    var running: Boolean = false,
) {
    fun call(program: Program, arguments: Arguments): Value<*> {
        if (!running) {
            running = true
            program.newScope()
        }

        arguments.namedArguments.forEach { (name: String, expression: Expression) ->
            val hasDefault = defaultParameters.stream().anyMatch { it.first == name }

            if (parameters.contains(name) || hasDefault) {
                program.scope.setLocal(name, expression.evaluate(program))
            } else {
                throw RunException("Provided extra argument '$name'")
            }
        }

        for (i in parameters.indices) {
            if (program.scope.getLocal(parameters[i]) == null) {
                if (arguments.namelessArguments.size > i) {
                    program.scope.setLocal(
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
                program.scope.setLocal(
                    defaultParameters[i - parameters.size].first,
                    arguments.namelessArguments[i].evaluate(program),
                )
            } else {
                throw RunException("Provided extra argument")
            }
        }

        for (parameter in defaultParameters) {
            if (program.scope.getLocal(parameter.first) == null) {
                program.scope.setLocal(parameter.first, parameter.second.evaluate(program))
            }
        }

        var returnValue: Value<*> = Null.VALUE
        val statement = statements.runNext(program)

        if (statement is ReturnStatement) {
            returnValue = statement.returnValue(program)
        }

        running = false
        program.endScope()
        return returnValue
    }
}
