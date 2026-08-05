package program.expression.value

import program.Program
import program.expression.Arguments
import program.expression.value.util.Range

class RangeValue(value: Range) : Value<Range>(value) {
    override fun typeString(): String = "range"

    override fun toList(): List<Value<*>> {
        return (value.start..<value.end step value.step).toList().map { IntegerValue(it) }
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return when (name) {
            "start" -> IntegerValue(value.start)
            "end" -> IntegerValue(value.end)
            "step" -> IntegerValue(value.step)
            else -> super.getItem(name)
        }
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return when (name) {
            "step" -> step()
            else -> super.innerCallFunction(name, local)
        }
    }

    context(program: Program, arguments: Arguments)
    fun step(): Value<*> {
        val step = get<IntegerValue>("value").value
        return RangeValue(Range(value.start, value.end, step))
    }

    override fun getStatic(): Static {
        return Companion
    }

    companion object : Static {
        override val name: String = "Range"

        context(program: Program, arguments: Arguments)
        override fun innerCall(): Value<*> {
            val start = get<IntegerValue>("start", IntegerValue(0)).value
            val end = get<IntegerValue>("end").value
            val step = get<IntegerValue>("step", IntegerValue(1)).value
            return RangeValue(Range(start, end, step))
        }
    }
}
