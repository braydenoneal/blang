package program.expression

import program.Program
import program.RunException
import program.expression.value.Value

data class Arguments(
    val namelessArguments: MutableList<Expression>,
    val namedArguments: MutableMap<String, Expression>,
    var index: Int = 0,
    var seen: MutableList<String> = mutableListOf(),
) {
    fun getAny(program: Program, name: String, default: Value<*>? = null): Value<*> {
        if (name in seen) {
            index = 0
            seen.clear()
        }

        seen.add(name)

        if (namelessArguments.size > index) {
            return namelessArguments[index++].evaluate(program)
        }

        for (argument in namedArguments) {
            if (argument.key == name) {
                return argument.value.evaluate(program)
            }
        }

        return default ?: throw RunException("Missing argument $name")
    }

    inline fun <reified T : Value<*>> get(program: Program, name: String, default: T? = null): T {
        return getAny(program, name, default).cast<T>()
    }

    companion object {
        val EMPTY: Arguments = Arguments(mutableListOf(), mutableMapOf())
    }
}
