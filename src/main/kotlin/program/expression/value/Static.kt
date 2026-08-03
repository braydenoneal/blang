package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments

interface Static {
    val name: String

    fun constructor(program: Program, arguments: Arguments): Value<*> {
        throw RunException("$name does not implement constructor", arguments.span)
    }

    fun getItem(name: String): Value<*>? {
        return null
    }

    fun getFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        return null
    }

    companion object {
        val staticCompanions: MutableMap<String, Static> = mutableMapOf()

        fun register(static: Static) {
            staticCompanions[static.name] = static
        }

        fun initialize() {
            register(RangeValue.Companion)
            register(PairValue.Companion)
        }
    }
}
