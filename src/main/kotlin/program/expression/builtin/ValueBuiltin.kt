package program.expression.builtin

import program.expression.Arguments
import program.expression.value.Value

abstract class ValueBuiltin<T : Value<*>>(open val value: T, override val arguments: Arguments) : Builtin(arguments)
