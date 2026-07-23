package program.expression.value.builtin

import program.expression.value.Value

abstract class ValueBuiltin<T : Value<*>>(open val value: T) : Builtin
