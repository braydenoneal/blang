package program.statement

import parser.Parser
import parser.tokenizer.Span
import program.Program

abstract class Statement {
    var span = Span.NONE

    fun withSpan(start: Int, parser: Parser): Statement {
        span = Span(start, parser.spanEnd())
        return this
    }

    context(program: Program)
    open fun execute(): Statement {
        try {
            val statement = innerExecute()
            done()
            return statement
        } catch (_: IncompleteException) {
            abort()
            throw IncompleteException()
        }
    }

    context(program: Program)
    open fun innerExecute(): Statement {
        return this
    }

    context(program: Program)
    open fun abort() {
    }

    context(program: Program)
    open fun done() {
    }
}
