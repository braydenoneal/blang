package program.expression

import program.Program
import program.RunException
import program.expression.value.*

data class Arguments(val namelessArguments: MutableList<Expression>, val namedArguments: MutableMap<String, Expression>) {
    fun anyValue(program: Program, name: String, index: Int): Value<*> {
        var expression = namedArguments[name]

        if (expression == null) {
            if (index >= namelessArguments.size) {
                throw RunException("Missing argument $name")
            }

            expression = namelessArguments[index]
        }

        return expression.evaluate(program)
    }

    fun booleanValue(program: Program, name: String, index: Int): BooleanValue {
        val value = anyValue(program, name, index)

        if (value is BooleanValue) {
            return value
        }

        throw RunException("$name is not a boolean")
    }

    fun floatValue(program: Program, name: String, index: Int): FloatValue {
        val value = anyValue(program, name, index)

        if (value is FloatValue) {
            return value
        }

        throw RunException("$name is not a float")
    }

    fun functionValue(program: Program, name: String, index: Int): FunctionValue {
        val value = anyValue(program, name, index)

        if (value is FunctionValue) {
            return value
        }

        throw RunException("$name is not a function")
    }

    fun integerValue(program: Program, name: String, index: Int): IntegerValue {
        val value = anyValue(program, name, index)

        if (value is IntegerValue) {
            return value
        }

        throw RunException("$name is not an integer")
    }

    fun listValue(program: Program, name: String, index: Int): ListValue {
        val value = anyValue(program, name, index)

        if (value is ListValue) {
            return value
        }

        throw RunException("$name is not a list")
    }

    @Suppress("unused")
    fun rangeValue(program: Program, name: String, index: Int): RangeValue {
        val value = anyValue(program, name, index)

        if (value is RangeValue) {
            return value
        }

        throw RunException("$name is not a range")
    }

    fun stringValue(program: Program, name: String, index: Int): StringValue {
        val value = anyValue(program, name, index)

        if (value is StringValue) {
            return value
        }

        throw RunException("$name is not a string")
    }

    companion object {
        val EMPTY: Arguments = Arguments(mutableListOf(), mutableMapOf())
    }
}
