package program.expression.value

import program.Program
import program.expression.Expression

abstract class Value<T>(val value: T) : Expression {
    override fun evaluate(program: Program): Value<*> {
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
        return value?.hashCode() ?: 0
    }
}
