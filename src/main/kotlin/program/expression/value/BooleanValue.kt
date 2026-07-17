package program.expression.value

class BooleanValue(value: Boolean) : Value<Boolean>(value) {
    override fun typeString(): String = "boolean"
}
