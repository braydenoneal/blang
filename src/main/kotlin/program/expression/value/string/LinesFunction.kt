package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.list.ListValue

object LinesFunction : ValueFunction<StringValue>() {
    override val name: String = "lines"

    context(program: Program, arguments: Arguments, value: StringValue)
    override fun call(): Value<*> {
        val lines: MutableList<Value<*>> = value.value.lines().map { string -> StringValue(string) }.toMutableList()
        return ListValue(lines)
    }
}
