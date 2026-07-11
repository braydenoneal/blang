package parser.statement

import parser.Parser
import parser.Program
import parser.expression.Expression
import parser.expression.value.Null
import parser.expression.value.Value
import tokenizer.Type

data class ReturnStatement(val expression: Expression) : Statement {
    override fun execute(program: Program): Statement {
        return this
    }

    fun returnValue(program: Program): Value<*>? {
        return expression.evaluate(program)
    }

    companion object {
        fun parse(parser: Parser): Statement {
            parser.expect(Type.KEYWORD, "return")
            val expression = if (parser.peekIs(Type.SEMICOLON) || parser.peekIsAllowNewline(Type.NEWLINE) || parser.peekIsAllowNewline(Type.CURLY_BRACE)) Null.VALUE else Expression.parse(parser)
            parser.expectStatementEnd()
            return ReturnStatement(expression)
        }
    }
}
