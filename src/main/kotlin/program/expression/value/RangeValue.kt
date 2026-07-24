package program.expression.value

import program.expression.value.util.Range

class RangeValue(value: Range) : Value<Range>(value) {
    override fun typeString(): String = "range"

    override fun toString(): String {
        return "range(" + value.start + ", " + value.end + ", " + value.step + ")"
    }
}
