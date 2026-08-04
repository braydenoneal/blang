package program.expression.value

import parser.tokenizer.Span
import program.Program
import program.RunException
import program.expression.Arguments
import java.util.Locale.getDefault

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

    abstract fun typeString(): String

    val capitalType get(): String = typeString().replaceFirstChar { it.titlecase(getDefault()) }

    inline fun <reified T : Value<*>> cast(): T {
        return this as? T ?: throw RunException("$capitalType is not of type ${T::class}", span)
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
        return getStatic()?.getItem(name) ?: throw RunException("Value has no item '$name'", span)
    }

    open fun assignItem(name: String, setValue: Value<*>): Value<*> {
        throw RunException("$capitalType has no assignable item $name", span)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return callBaseFunction(name) ?: getStatic()?.innerCallFunction(name) ?: throw RunException("$capitalType has no function '$name'")
    }

    context(program: Program, arguments: Arguments)
    fun callBaseFunction(name: String): Value<*>? {
        return when (name) {
            "toString" -> toStringValue()
            "to" -> to()
            else -> null
        }
    }

    fun toStringValue(): Value<*> {
        return StringValue(toString())
    }

    context(program: Program, arguments: Arguments)
    fun to(): Value<*> {
        return PairValue(this to arguments.getAny("second"))
    }

    open fun getStatic(): Static? {
        return null
    }
}
