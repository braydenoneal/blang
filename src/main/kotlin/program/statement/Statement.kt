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

    open fun execute(program: Program): Statement {
        try {
            val statement = innerExecute(program)
            done(program)
            return statement
        } catch (_: IncompleteException) {
            abort(program)
            throw IncompleteException()
        }
    }

    open fun innerExecute(program: Program): Statement {
        return this
    }

    open fun abort(program: Program) {}

    open fun done(program: Program) {}
}
