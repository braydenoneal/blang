package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Function

class FunctionValue(value: Function) : Value<Function>(value) {
    override fun typeString(): String = "function"

    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return value.innerCall(program, arguments)
    }

    override fun abort(program: Program, arguments: Arguments) {
        value.abort(program, arguments)
    }

    override fun done(program: Program, arguments: Arguments) {
        value.done(program, arguments)
    }
}
