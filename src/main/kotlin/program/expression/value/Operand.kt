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

    open fun equalsOther(other: T): Boolean {
        throw RunException("Value does not implement equals", span)
    }

    open fun rangeTo(other: T): Value<*> {
        throw RunException("Value does not implement rangeTo", span)
    }

    open fun toList(): List<Value<*>> {
        throw RunException("Value does not implement asList", span)
    }

    open fun fromList(list: List<Value<*>>): Value<*> {
        return ListValue(list.toMutableList())
    }

    fun size(): Int {
        return toList().size
    }

    fun wrapIndex(index: Int): Int {
        var index = index

        if (index >= size()) {
            throw RunException("Index $index out of range for list of size ${size()}", span)
        }

        while (index < 0) {
            index += size()
        }

        return index
    }

    fun asIndex(item: Value<*>): Int {
        return wrapIndex(item.cast<IntegerValue>().value)
    }

    fun get(item: Value<*>): Value<*> {
        return toList()[asIndex(item)]
    }

    fun sliceList(from: Value<*>?, to: Value<*>?): List<Value<*>> {
        val fromIndex = asIndex(from ?: IntegerValue(0))
        val toIndex = asIndex(to ?: IntegerValue(size() - 1))

        if (fromIndex > toIndex) {
            return toList().subList(toIndex, fromIndex).reversed().toMutableList()
        }

        return toList().subList(fromIndex, toIndex)
    }

    fun slice(from: Value<*>?, to: Value<*>?): Value<*> {
        return fromList(sliceList(from, to))
    }

    open fun contains(item: Value<*>): Boolean {
        return item in toList()
    }

    open fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        throw RunException("Value does not implement set", span)
    }
}
