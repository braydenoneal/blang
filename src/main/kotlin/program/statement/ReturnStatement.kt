package program.statement

import parser.Parser
import parser.tokenizer.Type
import program.Program
import program.expression.Expression
import program.expression.value.Null
import program.expression.value.Value

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
            val expression = if (parser.peekIs(Type.SEMICOLON) || parser.peekIsAllowNewline(Type.NEWLINE) || parser.peekIsAllowNewline(Type.RIGHT_CURLY_BRACE)) Null.VALUE else Expression.parse(parser)
            parser.expectStatementEnd()
            return ReturnStatement(expression)
        }
    }
}
