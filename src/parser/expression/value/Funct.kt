package parser.expression.value

import parser.Program
import parser.RunException
import parser.expression.Arguments
import parser.expression.Expression
import parser.statement.ReturnStatement
import parser.statement.Statement
import java.util.function.Consumer

data class Funct(
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val statements: MutableList<Statement>
) {
    fun call(program: Program, arguments: Arguments): Value<*> {
        program.newScope()

        arguments.namedArguments.forEach { name: String, expression: Expression ->
            val hasDefault = this.defaultParameters.stream().anyMatch { it.first == name }
            if (parameters.contains(name) || hasDefault) {
                program.scope.setLocal(name, expression.evaluate(program))
            } else {
                throw RunException("Provided extra argument '$name'")
            }
        }

        for (i in parameters.indices) {
            if (program.scope.getLocal(parameters[i]) == null) {
                if (arguments.arguments.size > i) {
                    program.scope.setLocal(
                        parameters[i],
                        arguments.arguments[i].evaluate(program)
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
                    arguments.arguments[i].evaluate(program)
                )
            } else {
                throw RunException("Provided extra argument")
            }
        }

        defaultParameters.forEach(Consumer {
            if (program.scope.getLocal(it.first) == null) {
                program.scope.setLocal(it.first, it.second.evaluate(program))
            }
        })

        var returnValue: Value<*> = Null.VALUE
        val statement = Statement.runStatements(program, statements)

        if (statement is ReturnStatement) {
            returnValue = statement.returnValue(program)
        }

        program.endScope()
        return returnValue
    }
}
