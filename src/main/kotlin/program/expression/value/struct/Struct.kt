package program.expression.value.struct

import program.expression.value.Value

class Struct(
    val definition: StructDefinition,
    val variables: MutableMap<String, Value<*>>,
) {
    override fun toString(): String {
        val strings = mutableListOf<String>()

        for ((name, value) in variables) {
            strings.add("$name = $value")
        }

        return "${definition.name}(${strings.joinToString(", ")})"
    }
}
