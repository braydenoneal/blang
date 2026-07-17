package program.expression.builtin

import program.expression.Arguments
import program.expression.Expression

abstract class Builtin(open val arguments: Arguments) : Expression
