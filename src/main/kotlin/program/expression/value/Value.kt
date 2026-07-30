package program.expression.value

import parser.tokenizer.Span
import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.value.util.FunctionReference

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

    inline fun <reified T : Value<*>> cast(): T {
        return this as? T ?: throw RunException("Value is not of type ${T::class}", span)
    }

    @Suppress("UNCHECKED_CAST")
    fun cast(other: Value<*>): T {
        if (other.value::class.isInstance(value)) {
            return other.value as T
        }

        throw RunException("Values are not the same type", span)
    }

    open fun getItem(program: Program, name: String): Value<*> {
        return FunctionReferenceValue(FunctionReference(this, name)).withSpan(span)
    }

    open fun assignItem(name: String, setValue: Value<*>): Value<*> {
        throw RunException("Value has no assignable item $name", span)
    }

    open fun getFunction(program: Program, name: String): ((Program, Arguments) -> Value<*>)? {
        return null
    }

    fun getBaseFunction(name: String): ((Program, Arguments) -> Value<*>)? {
        return when (name) {
            "toString" -> ::toStringValue
            else -> null
        }
    }

    fun toStringValue(
        @Suppress("unused")
        program: Program,
        @Suppress("unused")
        arguments: Arguments,
    ): Value<*> {
        return StringValue(toString())
    }

    fun callFunction(program: Program, arguments: Arguments, name: String): Value<*> {
        val function = getFunction(program, name) ?: getBaseFunction(name) ?: throw RunException("Value has no functions")
        return function.invoke(program, arguments)
    }

    open fun iteratorGet(index: Int): Value<*> {
        throw RunException("Value does not implement iterator get", span)
    }

    open fun iteratorSize(): Int {
        throw RunException("Value does not implement iterator size", span)
    }
}
