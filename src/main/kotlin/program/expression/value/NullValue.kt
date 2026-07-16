package program.expression.value

class NullValue(value: Null) : Value<Null>(value) {
    override fun toString(): String {
        return "null"
    }
}
