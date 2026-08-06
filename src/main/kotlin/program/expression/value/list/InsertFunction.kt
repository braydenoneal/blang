package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.integer.IntegerValue

object InsertFunction : ValueFunction<ListValue>() {
    override val name: String = "insert"

    context(program: Program, arguments: Arguments, value: ListValue)
    override fun call(): Value<*> {
        val index = get<IntegerValue>("index").value
        val insertValue = getAny("value")
        value.value.add(index, insertValue)
        return value
    }
}
