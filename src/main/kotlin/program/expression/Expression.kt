package program.expression

import parser.Parser
import parser.tokenizer.Span
import program.Program
import program.expression.value.Value
import program.statement.IncompleteException

abstract class Expression {
    var span = Span.NONE

    fun withSpan(start: Int, parser: Parser): Expression {
        span = Span(start, parser.spanEnd())
        return this
    }

    open fun evaluate(program: Program): Value<*> {
        try {
            val value = innerEvaluate(program).withSpan(span)
            done(program)
            return value
        } catch (_: IncompleteException) {
            abort(program)
            throw IncompleteException()
        }
    }

    abstract fun innerEvaluate(program: Program): Value<*>

    open fun abort(program: Program) {}

    open fun done(program: Program) {}
}
