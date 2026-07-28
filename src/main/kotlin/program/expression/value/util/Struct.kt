package program.expression.value.util

import program.expression.value.Value

class Struct(
    val definition: StructDefinition,
    val variables: MutableMap<String, Value<*>>,
)
