package program.expression.value.pair

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value
import program.expression.value.getAny

class PairValue(value: Pair<Value<*>, Value<*>>) : Value<Pair<Value<*>, Value<*>>>(value) {
    override fun toString(): String {
        return "${value.first} to ${value.second}"
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

    companion object : Static<PairValue>() {
        override val name: String = "Pair"

        context(program: Program, arguments: Arguments)
        override fun innerCall(): Value<*> {
            return PairValue(getAny("first") to getAny("second"))
        }

        override fun initializeItems() {
            register(FirstItem)
            register(SecondItem)
        }
    }
}
