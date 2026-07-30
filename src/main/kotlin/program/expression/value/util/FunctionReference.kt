package program.expression.value.util

import program.expression.value.Value

class FunctionReference(val value: Value<*>?, val name: String) {
    override fun toString(): String {
        return name
    }
}
