package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Range

class RangeValue(value: Range) : Value<Range>(value) {
    override fun typeString(): String = "range"

    override fun toList(): List<Value<*>> {
        return (value.start..<value.end step value.step).toList().map { IntegerValue(it) }
    }

    override fun getItem(program: Program, name: String): Value<*> {
        return when (name) {
            "start" -> IntegerValue(value.start)
            "end" -> IntegerValue(value.end)
            "step" -> IntegerValue(value.step)
            else -> super.getItem(program, name)
        }
    }

    override fun innerCallFunction(program: Program, arguments: Arguments, name: String, local: Boolean): Value<*> {
        return when (name) {
            "step" -> step(program, arguments)
            else -> super.innerCallFunction(program, arguments, name, local)
        }
    }

    fun step(program: Program, arguments: Arguments): Value<*> {
        val step = arguments.get<IntegerValue>(program, "value").value
        return RangeValue(Range(value.start, value.end, step))
    }

    override fun getStatic(): Static {
        return Companion
    }

    companion object : Static {
        override val name: String = "Range"

        override fun innerCall(program: Program, arguments: Arguments): Value<*> {
            val start = arguments.get<IntegerValue>(program, "start", IntegerValue(0)).value
            val end = arguments.get<IntegerValue>(program, "end").value
            val step = arguments.get<IntegerValue>(program, "step", IntegerValue(1)).value
            return RangeValue(Range(start, end, step))
        }
    }
}
