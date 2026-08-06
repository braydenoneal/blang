package program.expression.value.struct

import program.expression.Expression
import program.expression.value.function.FunctionValue

class StructDefinition(
    val name: String,
    val parameters: MutableList<String>,
    val defaultParameters: MutableList<Pair<String, Expression>>,
    val functions: MutableMap<String, FunctionValue>,
    val staticFunctions: MutableMap<String, FunctionValue>,
    val staticVariables: MutableMap<String, Expression>,
) {
    override fun toString(): String {
        val strings = mutableListOf<String>()

        for (name in parameters) {
            strings.add(name)
        }

        for ((name, _) in defaultParameters) {
            strings.add(name)
        }

        return "$name(${strings.joinToString(", ")})"
    }
}
