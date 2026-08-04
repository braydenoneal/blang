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

    context(program: Program)
    open fun evaluate(): Value<*> {
        try {
            val value = innerEvaluate().withSpan(span)
            done()
            return value
        } catch (_: IncompleteException) {
            abort()
            throw IncompleteException()
        }
    }

    context(program: Program)
    abstract fun innerEvaluate(): Value<*>

    context(program: Program)
    open fun abort() {
    }

    context(program: Program)
    open fun done() {
    }
}
