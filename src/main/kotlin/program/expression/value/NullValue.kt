package program.expression.value

class NullValue(value: Null) : Value<Null>(value) {
    override fun typeString(): String = "null"

    override fun toString(): String {
        return "null"
    }
}
