package program.expression.value

import program.Program

abstract class StaticItem {
    abstract val name: String

    context(program: Program)
    abstract fun get(): Value<*>
}
