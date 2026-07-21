package program.expression

import program.Program
import program.expression.value.Value
import program.statement.IncompleteException

interface Expression {
    fun evaluate(program: Program): Value<*> {
        try {
            val value = innerEvaluate(program)
            done(program)
            return value
        } catch (_: IncompleteException) {
            abort(program)
            throw IncompleteException()
        }
    }

    fun innerEvaluate(program: Program): Value<*>

    fun abort(program: Program) {}

    fun done(program: Program) {}
}
