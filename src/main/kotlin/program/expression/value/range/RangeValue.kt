package program.expression.value.range

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value
import program.expression.value.get
import program.expression.value.integer.IntegerValue

class RangeValue(value: Range) : Value<Range>(value) {
    override fun toList(): List<Value<*>> {
        return (value.start..<value.end step value.step).toList().map { IntegerValue(it) }
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<RangeValue>() {
        override val name = "Range"

        context(program: Program, arguments: Arguments)
        override fun innerCall(): Value<*> {
            val start = get<IntegerValue>("start", IntegerValue(0)).value
            val end = get<IntegerValue>("end").value
            val step = get<IntegerValue>("step", IntegerValue(1)).value
            return RangeValue(Range(start, end, step))
        }

        override fun initializeItems() {
            register(StartItem)
            register(EndItem)
            register(StepItem)
        }

        override fun initializeFunctions() {
            register(StepFunction)
        }
    }
}
