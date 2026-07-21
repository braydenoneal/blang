package program.expression.value

import program.Program
import program.RunException
import program.expression.Expression

abstract class Value<T>(val value: T) : Expression {
    override fun innerEvaluate(program: Program): Value<*> {
        return this
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Value<*>) {
            return value == other.value
        }

        return super.equals(other)
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    abstract fun typeString(): String

    inline fun <reified T : Value<*>> cast(): T {
        return this as? T ?: throw RunException("Value is not of type ${T::class}")
    }
}
