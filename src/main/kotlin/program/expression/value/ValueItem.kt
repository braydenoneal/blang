package program.expression.value

import program.Program

abstract class ValueItem<T : Value<*>> {
    abstract val name: String

    context(program: Program, value: T)
    abstract fun get(): Value<*>
}
