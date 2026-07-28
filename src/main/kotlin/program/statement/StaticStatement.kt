package program.statement

import program.expression.Expression
import program.expression.value.FunctionValue

class StaticStatement(val functions: MutableMap<String, FunctionValue>, val variables: MutableMap<String, Expression>) : Statement
