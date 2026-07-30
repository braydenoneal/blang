package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

class ReturnStatement(val expression: Expression) : Statement() {
    fun returnValue(program: Program): Value<*> {
        return expression.evaluate(program)
    }

    override fun toString(): String {
        return "return $expression"
    }
}
