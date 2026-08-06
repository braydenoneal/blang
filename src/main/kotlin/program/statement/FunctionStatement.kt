package program.statement

import program.expression.value.function.Function

class FunctionStatement(val name: String, val function: Function) : Statement() {
    override fun toString(): String {
        return "fn $name$function"
    }
}
