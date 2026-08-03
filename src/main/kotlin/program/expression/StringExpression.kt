package program.expression

import program.Program
import program.expression.value.StringValue
import program.expression.value.Value

class StringExpression(
    val stringExpressionPairs: MutableList<Pair<String, Expression>>,
    val finalString: String,
    var values: MutableList<String> = mutableListOf(),
) : Expression() {
    override fun innerEvaluate(program: Program): Value<*> {
        val totalString = StringBuilder()

        for (i in stringExpressionPairs.indices) {
            val (string, expression) = stringExpressionPairs[i]
            val value: String

            if (i < values.size) {
                value = values[i]
            } else {
                value = expression.evaluate(program).toString()
                values.add(value)
            }

            totalString.append("$string${value}")
        }

        totalString.append(finalString)
        return StringValue(totalString.toString())
    }

    override fun done(program: Program) {
        values.clear()
    }

    override fun toString(): String {
        return "${stringExpressionPairs.forEach { "${it.first}{${it.second}}" }}$finalString"
    }
}
