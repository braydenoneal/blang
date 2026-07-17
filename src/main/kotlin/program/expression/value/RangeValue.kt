package program.expression.value

class RangeValue(value: Range) : Value<Range>(value) {
    override fun typeString(): String = "range"

    override fun toString(): String {
        return "range(" + value.start + ", " + value.end + ", " + value.step + ")"
    }
}
