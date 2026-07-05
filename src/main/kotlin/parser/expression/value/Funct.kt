package parser.expression.value

import parser.Program
import parser.RunException
import parser.expression.Arguments
import parser.expression.Expression
import parser.statement.ReturnStatement
import parser.statement.StatementList

data class Funct(
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val statements: StatementList,
) {
    fun call(program: Program, arguments: Arguments): Value<*>? {
        program.newScope()

        arguments.namedArguments.forEach { (name: String, expression: Expression) ->
            val hasDefault = this.defaultParameters.stream().anyMatch { it.first == name }

            if (parameters.contains(name) || hasDefault) {
                program.scope.setLocal(name, expression.evaluate(program) ?: return null)
            } else {
                throw RunException("Provided extra argument '$name'")
            }
        }

        for (i in parameters.indices) {
            if (program.scope.getLocal(parameters[i]) == null) {
                if (arguments.arguments.size > i) {
                    program.scope.setLocal(
                        parameters[i],
                        arguments.arguments[i].evaluate(program) ?: return null
                    )
                } else {
                    throw RunException("Missing argument '" + parameters[i] + "'")
                }
            }
        }

        for (i in parameters.size..<arguments.arguments.size) {
            if (defaultParameters.size > i - parameters.size) {
                program.scope.setLocal(
                    defaultParameters[i - parameters.size].first,
                    arguments.arguments[i].evaluate(program) ?: return null
                )
            } else {
                throw RunException("Provided extra argument")
            }
        }

        for (parameter in defaultParameters) {
            if (program.scope.getLocal(parameter.first) == null) {
                program.scope.setLocal(parameter.first, parameter.second.evaluate(program) ?: return null)
            }
        }

        var returnValue: Value<*> = Null.VALUE
        val statement = statements.runNext(program)

        if (statement is ReturnStatement) {
            returnValue = statement.returnValue(program) ?: return null
        }

        program.endScope()
        return returnValue
    }
}
