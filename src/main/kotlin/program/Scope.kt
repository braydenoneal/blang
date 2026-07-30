package program

import parser.tokenizer.Span
import program.expression.value.Value
import program.expression.value.util.Null

class Scope(
    val parent: Scope?,
    var variables: MutableMap<String, Value<*>> = mutableMapOf(),
) {
    fun getNullable(name: String): Value<*>? {
        val value = variables[name]

        if (value == null && parent != null) {
            return parent.getNullable(name)
        }

        return value
    }

    fun get(name: String, span: Span = Span.NONE): Value<*> {
        return getNullable(name) ?: throw RunException("Variable '$name' does not exist", span)
    }

    fun parentWithVariable(name: String): Scope? {
        if (variables.containsKey(name)) {
            return this
        }

        return parent?.parentWithVariable(name)
    }

    fun set(name: String, value: Value<*>): Value<*> {
        if (name == "_") {
            return Null.VALUE
        }

        val parentWithVariable = parentWithVariable(name)

        if (parentWithVariable != null) {
            parentWithVariable.variables[name] = value
            return value
        }

        variables[name] = value
        return value
    }

    fun delete(name: String): Value<*>? {
        val value = variables.remove(name)

        if (value == null && parent != null) {
            return parent.delete(name)
        }

        return value
    }

    fun setLocal(name: String, value: Value<*>): Value<*> {
        if (name == "_") {
            return Null.VALUE
        }

        variables[name] = value
        return value
    }
}
