package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.Expression
import parser.expression.value.Null
import parser.expression.value.StringValue
import parser.expression.value.Value

data class PrintBuiltin(val arguments: Arguments) : Expression {
    override fun evaluate(program: Program): Value<*>? {
        val value = if (arguments.namelessArguments.isEmpty()) StringValue("") else arguments.anyValue(program, "value", 0) ?: return null
        var string = value.toString()

        if (value is StringValue) {
            string = string.substring(1, string.length - 1)
        }

        println(string)
        return Null.VALUE
    }
}
