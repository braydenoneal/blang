package program.expression.value

class StringValue(value: String) : Value<String>(value) {
    override fun typeString(): String = "string"

    override fun toString(): String {
        return "\"" + value + "\""
    }
}
