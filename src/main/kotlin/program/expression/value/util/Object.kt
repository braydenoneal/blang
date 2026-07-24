package program.expression.value.util

import program.expression.value.Value

class Object(
    val items: MutableMap<String, Value<*>>,
    val functions: MutableMap<String, Function>,
)
