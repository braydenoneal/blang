package program.expression.value

class IntegerValue(value: Int) : Value<Int>(value) {
    override fun typeString(): String = "integer"
}
