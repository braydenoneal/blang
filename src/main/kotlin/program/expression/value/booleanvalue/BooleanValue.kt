package program.expression.value.booleanvalue

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value

class BooleanValue(value: Boolean) : Value<Boolean>(value) {
    override fun truth(): Boolean {
        return value
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<BooleanValue>() {
        override val name = "Boolean"
    }
}
