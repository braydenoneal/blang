package parser.statement

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
        fun parse(program: Program): Statement {
            program.expect(Type.KEYWORD, "return")
            val expression = if (program.peekIs(Type.SEMICOLON)) Null.VALUE else Expression.parse(program)
            program.expect(Type.SEMICOLON)
            return ReturnStatement(expression)
        }
    }
}
