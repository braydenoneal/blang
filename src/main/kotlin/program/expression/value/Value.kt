package program.expression.value

import program.Program
import program.RunException
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.util.FunctionReference
import program.statement.IncompleteException

abstract class Value<T>(open val value: T) : Expression, Operand<T>() {
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

    @Suppress("UNCHECKED_CAST")
    fun cast(other: Value<*>): T {
        if (other.value::class.isInstance(value)) {
            return other.value as T
        }

        throw RunException("Values are not the same type")
    }

    fun call(program: Program, arguments: Arguments): Value<*> {
        try {
            val value = innerCall(program, arguments)
            done(program, arguments)
            return value
        } catch (_: IncompleteException) {
            abort(program, arguments)
            throw IncompleteException()
        }
    }

    open fun innerCall(program: Program, arguments: Arguments): Value<*> {
        throw RunException("Value cannot be called")
    }

    open fun abort(program: Program, arguments: Arguments) {
        arguments.abort()
    }

    open fun done(program: Program, arguments: Arguments) {
        arguments.done()
    }

    open fun getItem(name: String): Value<*> {
        return FunctionReferenceValue(FunctionReference(this, name))
    }

    open fun getFunction(name: String): (Program, Arguments) -> Value<*> {
        throw RunException("Value has no functions")
    }

    fun callFunction(program: Program, arguments: Arguments, name: String): Value<*> {
        return getFunction(name).invoke(program, arguments)
    }
}
