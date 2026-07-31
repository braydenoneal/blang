package program.expression.value.util

import program.expression.value.Value

class ValueIdentifier(val value: Value<*>, val name: String) {
    override fun toString(): String {
        return "$value.$name"
    }
}
