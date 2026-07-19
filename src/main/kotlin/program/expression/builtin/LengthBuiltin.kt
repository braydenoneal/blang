package program.expression.builtin

import program.Program
import program.expression.Arguments
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.Value

data class LengthBuiltin(override val arguments: Arguments) : Builtin(arguments), Expression {
    override fun evaluate(program: Program): Value<*> {
        return IntegerValue(arguments.get<ListValue>(program, "value").value.size)
    }
}
