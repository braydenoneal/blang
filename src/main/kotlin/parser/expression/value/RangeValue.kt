package parser.expression.value


class RangeValue(value: Range) : Value<Range>(value) {
    override fun toString(): String {
        return "range(" + value.start + ", " + value.end + ", " + value.step + ")"
    }
}
