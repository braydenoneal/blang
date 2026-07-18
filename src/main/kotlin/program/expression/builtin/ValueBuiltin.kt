package program.expression.builtin

import program.expression.Arguments
import program.expression.Expression
import program.expression.value.Value

abstract class ValueBuiltin<T : Value<*>>(open val value: T, open val arguments: Arguments) : Expression
