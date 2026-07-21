package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

data class ReturnStatement(val expression: Expression) : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }

    fun returnValue(program: Program): Value<*> {
        return expression.evaluate(program)
    }
}
