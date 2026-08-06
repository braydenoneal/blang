package program.expression.value

import parser.tokenizer.Span
import program.Program
import program.RunException
import program.expression.Arguments

abstract class Value<T>(open val value: T) : Operand<T>(), Callable {
    context(program: Program)
    override fun innerEvaluate(): Value<*> {
        return this
    }

    fun withSpan(span: Span): Value<*> {
        this.span = span
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

    override fun equalsOther(other: T): Boolean {
        return value == other
    }

    inline fun <reified T : Value<*>> cast(): T {
        return this as? T ?: throw RunException("${static.name} is not of type ${T::class}", span)
    }

    @Suppress("UNCHECKED_CAST")
    fun cast(other: Value<*>): T {
        if (other.value::class.isInstance(value)) {
            return other.value as T
        }

        throw RunException("Values are not the same type", span)
    }

    context(program: Program)
    open fun getItem(name: String): Value<*> {
        throw RunException("Value has no item '$name'", span)
    }

    open fun assignItem(name: String, setValue: Value<*>): Value<*> {
        throw RunException("${static.name} has no assignable item $name", span)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        throw RunException("${static.name} has no function '$name'")
    }

    abstract val static: Static<out Value<T>>
}
