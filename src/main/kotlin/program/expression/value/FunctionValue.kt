package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Function

class FunctionValue(value: Function) : Value<Function>(value) {
    override fun typeString(): String = "function"

    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return value.innerCall()
    }

    context(program: Program, arguments: Arguments)
    override fun abort() {
        value.abort()
    }

    context(program: Program, arguments: Arguments)
    override fun done() {
        value.done()
    }
}
