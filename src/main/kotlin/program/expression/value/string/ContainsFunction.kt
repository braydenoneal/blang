package program.expression.value.string

import program.Program
import program.expression.Arguments
import program.expression.value.Value
import program.expression.value.ValueFunction
import program.expression.value.booleanvalue.BooleanValue

object ContainsFunction : ValueFunction<StringValue>() {
    override val name: String = "contains"

    context(program: Program, arguments: Arguments, value: StringValue)
    override fun call(): Value<*> {
        val item = get<StringValue>("value").value
        val ignoreCase = get<BooleanValue>("ignoreCase", BooleanValue(false)).value
        return BooleanValue(value.value.contains(item, ignoreCase))
    }
}
