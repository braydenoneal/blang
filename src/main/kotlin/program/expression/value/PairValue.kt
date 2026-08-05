package program.expression.value

import program.Program
import program.expression.Arguments

class PairValue(value: Pair<Value<*>, Value<*>>) : Value<Pair<Value<*>, Value<*>>>(value) {
    override fun typeString(): String = "pair"

    override fun toString(): String {
        return "${value.first} to ${value.second}"
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return when (name) {
            "first" -> value.first
            "second" -> value.second
            else -> super.getItem(name)
        }
    }

    companion object : Static {
        override val name: String = "Pair"

        context(program: Program, arguments: Arguments)
        override fun innerCall(): Value<*> {
            return PairValue(getAny("first") to getAny("second"))
        }
    }
}
