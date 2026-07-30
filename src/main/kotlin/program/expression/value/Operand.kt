package program.expression.value

import program.RunException
import program.expression.Expression

abstract class Operand<T> : Expression() {
    open fun negative(): Value<*> {
        throw RunException("Value does not implement negative", span)
    }

    open fun positive(): Value<*> {
        throw RunException("Value does not implement positive", span)
    }

    open fun minus(other: T): Value<*> {
        throw RunException("Value does not implement minus", span)
    }

    open fun plus(other: T): Value<*> {
        throw RunException("Value does not implement add", span)
    }

    open fun floorDivide(other: T): Value<*> {
        throw RunException("Value does not implement floorDivide", span)
    }

    open fun divide(other: T): Value<*> {
        throw RunException("Value does not implement divide", span)
    }

    open fun remainder(other: T): Value<*> {
        throw RunException("Value does not implement remainder", span)
    }

    open fun times(other: T): Value<*> {
        throw RunException("Value does not implement times", span)
    }

    open fun exponentiate(other: T): Value<*> {
        throw RunException("Value does not implement exponentiate", span)
    }

    open fun compareTo(other: T): Int {
        throw RunException("Value does not implement compareTo", span)
    }

    open fun truth(): Boolean {
        throw RunException("Value does not implement truth", span)
    }

    open fun get(item: Value<*>): Value<*> {
        throw RunException("Value does not implement get", span)
    }

    open fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        throw RunException("Value does not implement set", span)
    }

    open fun equalsOther(other: T): Boolean {
        throw RunException("Value does not implement equals", span)
    }

    open fun rangeTo(other: T): Value<*> {
        throw RunException("Value does not implement rangeTo", span)
    }

    open fun contains(item: Value<*>): Boolean {
        throw RunException("Value does not implement contains", span)
    }
}
