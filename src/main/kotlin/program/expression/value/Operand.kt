package program.expression.value

import program.RunException

abstract class Operand<T> {
    open fun negative(): Value<*> {
        throw RunException("Value does not implement negative")
    }

    open fun positive(): Value<*> {
        throw RunException("Value does not implement positive")
    }

    open fun minus(other: T): Value<*> {
        throw RunException("Value does not implement minus")
    }

    open fun plus(other: T): Value<*> {
        throw RunException("Value does not implement add")
    }

    open fun floorDivide(other: T): Value<*> {
        throw RunException("Value does not implement floorDivide")
    }

    open fun divide(other: T): Value<*> {
        throw RunException("Value does not implement divide")
    }

    open fun remainder(other: T): Value<*> {
        throw RunException("Value does not implement remainder")
    }

    open fun times(other: T): Value<*> {
        throw RunException("Value does not implement times")
    }

    open fun exponentiate(other: T): Value<*> {
        throw RunException("Value does not implement exponentiate")
    }

    open fun compareTo(other: T): Int {
        throw RunException("Value does not implement compareTo")
    }

    open fun truth(): Boolean {
        throw RunException("Value does not implement truth")
    }

    open fun get(item: Value<*>): Value<*> {
        throw RunException("Value does not implement get")
    }

    open fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        throw RunException("Value does not implement set")
    }

    open fun equalsOther(other: T): Boolean {
        throw RunException("Value does not implement equals")
    }

    open fun rangeTo(other: T): Value<*> {
        throw RunException("Value does not implement rangeTo")
    }
}
