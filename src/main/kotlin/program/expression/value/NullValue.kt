package program.expression.value

import program.expression.value.util.Null

class NullValue(value: Null) : Value<Null>(value) {
    override fun typeString(): String = "null"

    override fun toString(): String {
        return "null"
    }
}
