package program.statement

import program.Program
import program.expression.Expression
import program.expression.value.Value

class ReturnStatement(val expression: Expression) : Statement() {
    context(program: Program)
    fun returnValue(): Value<*> {
        return expression.evaluate()
    }

    override fun toString(): String {
        return "return $expression"
    }
}
