package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Object

class ObjectValue(value: Object) : Value<Object>(value) {
    override fun typeString(): String = "object"

    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return super.innerCall(program, arguments)
    }
}
