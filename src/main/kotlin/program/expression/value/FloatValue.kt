package program.expression.value

class FloatValue(value: Float) : Value<Float>(value) {
    override fun typeString(): String = "float"
}
