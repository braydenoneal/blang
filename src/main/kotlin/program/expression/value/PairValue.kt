package program.expression.value

import program.Program
import program.expression.Arguments

class PairValue(value: Pair<Value<*>, Value<*>>) : Value<Pair<Value<*>, Value<*>>>(value) {
    override fun typeString(): String = "pair"

    override fun toString(): String {
        return "${value.first} to ${value.second}"
    }

    override fun getItem(program: Program, name: String): Value<*> {
        return when (name) {
            "first" -> value.first
            "second" -> value.second
            else -> super.getItem(program, name)
        }
    }

    companion object : Static {
        override val name: String = "Pair"

        override fun innerCall(program: Program, arguments: Arguments): Value<*> {
            return PairValue(arguments.getAny(program, "first") to arguments.getAny(program, "second"))
        }
    }
}
