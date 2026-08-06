package program.statement

import program.expression.Expression
import program.expression.value.function.FunctionValue

class StaticStatement(val functions: MutableMap<String, FunctionValue>, val variables: MutableMap<String, Expression>) : Statement()
