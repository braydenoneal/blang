package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Range

class RangeValue(value: Range) : Value<Range>(value) {
    override fun typeString(): String = "range"

    override fun toString(): String {
        return "range(" + value.start + ", " + value.end + ", " + value.step + ")"
    }

    override fun iteratorGet(index: Int): Value<*> {
        return IntegerValue(value.start + index * value.step)
    }

    override fun iteratorSize(): Int {
        return (value.end - value.start) / value.step
    }

    override fun getFunction(name: String): (Program, Arguments) -> Value<*> {
        return when (name) {
            "step" -> ::step
            else -> super.getFunction(name)
        }
    }

    fun step(program: Program, arguments: Arguments): Value<*> {
        val step = arguments.get<IntegerValue>(program, "value").value
        return RangeValue(Range(value.start, value.end, step))
    }
}
