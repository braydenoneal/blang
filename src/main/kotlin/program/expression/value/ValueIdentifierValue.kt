package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.ValueIdentifier

/**
 * A value that contains a reference to a function of an instance of a value.
 * For example, `[0, 1, 2].append` would evaluate to an instance of this class.
 * When called, it evaluates the function on the value with the passed in arguments.
 */
class ValueIdentifierValue(value: ValueIdentifier) : Value<ValueIdentifier>(value) {
    override fun typeString(): String = "valueIdentifier"

    override fun innerCall(program: Program, arguments: Arguments): Value<*> {
        return value.value.callFunction(program, arguments, value.name)
    }
}
