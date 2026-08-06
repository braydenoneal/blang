package program.expression.value.nullvalue

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value

class NullValue(value: Null) : Value<Null>(value) {
    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<NullValue>() {
        override val name = "Null"
    }
}
