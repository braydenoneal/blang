package program.expression.value

import parser.tokenizer.Span
import program.Program
import program.RunException
import program.expression.Arguments
import java.util.Locale.getDefault

abstract class Value<T>(open val value: T) : Operand<T>(), Callable {
    override fun innerEvaluate(program: Program): Value<*> {
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

    open fun getItem(program: Program, name: String): Value<*> {
        return getStatic()?.getItem(name) ?: throw RunException("Value has no item '$name'", span)
    }

    open fun assignItem(name: String, setValue: Value<*>): Value<*> {
        throw RunException("$capitalType has no assignable item $name", span)
    }

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        return callBaseFunction(program, arguments, name) ?: getStatic()?.innerCallFunction(program, arguments, name) ?: throw RunException("$capitalType has no function '$name'")
    }

    fun callBaseFunction(program: Program, arguments: Arguments, name: String): Value<*>? {
        return when (name) {
            "toString" -> toStringValue(program, arguments)
            "to" -> to(program, arguments)
            else -> null
        }
    }

    fun toStringValue(@Suppress("unused") program: Program, @Suppress("unused") arguments: Arguments): Value<*> {
        return StringValue(toString())
    }

    fun to(program: Program, arguments: Arguments): Value<*> {
        return PairValue(this to arguments.getAny(program, "second"))
    }

    open fun getStatic(): Static? {
        return null
    }
}
