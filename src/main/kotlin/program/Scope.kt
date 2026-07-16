package program

import program.expression.value.Value

class Scope(
    val parent: Scope?,
    var variables: MutableMap<String, Value<*>> = mutableMapOf(),
) {
    fun get(name: String): Value<*> {
        val value = variables[name]

        if (value == null && parent != null) {
            return parent.get(name)
        }

        return value ?: throw RunException("Variable '$name' does not exist")
    }

    fun parentWithVariable(name: String): Scope? {
        if (variables.containsKey(name)) {
            return this
        }

        return parent?.parentWithVariable(name)
    }

    fun set(name: String, value: Value<*>): Value<*> {
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

    fun setLocal(name: String, value: Value<*>) {
        variables[name] = value
    }

    fun getLocal(name: String): Value<*>? {
        return variables[name]
    }
}