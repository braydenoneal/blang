package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression

abstract class Builtin(open val arguments: Arguments) : Expression {
    override fun abort(program: Program) {
        arguments.abort()
    }

    override fun done(program: Program) {
        arguments.done()
    }
}
