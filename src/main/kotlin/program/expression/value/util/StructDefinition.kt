package program.expression.value.util

import program.expression.Expression
import program.expression.value.FunctionValue

class StructDefinition(
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val functions: MutableMap<String, FunctionValue>,
    val staticFunctions: MutableMap<String, FunctionValue>,
    val staticVariables: MutableMap<String, Expression>,
)
