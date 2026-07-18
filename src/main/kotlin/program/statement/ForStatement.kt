package program.statement

import program.Program
import program.RunException
import program.expression.Expression
import program.expression.value.IntegerValue
import program.expression.value.ListValue
import program.expression.value.RangeValue
import program.expression.value.Value

data class ForStatement(
    val itemName: String,
    val listExpression: Expression,
    val statements: StatementList,
    var listValue: Value<*>? = null,
    var index: Int = 0,
) : Statement {
    override fun execute(program: Program): Statement {
        if (listValue == null) {
            val listResult = listExpression.evaluate(program)
            listValue = listResult
        }

        val value = listValue

        if (value is ListValue) {
            val item = value.value[index]

            program.scope.set(itemName, item)
            val result = statements.runNext(program)

            if (result is ReturnStatement || result is BreakStatement) {
                listValue = null
                index = 0
                return result as? ReturnStatement ?: this
            }

            index++

            if (index >= value.value.size) {
                listValue = null
                index = 0
                return this
            }

            throw IncompleteException()
        } else if (value is RangeValue) {
            val number = value.value.start + index * value.value.step

            program.scope.set(itemName, IntegerValue(number))
            val result = statements.runNext(program)

            if (result is ReturnStatement || result is BreakStatement) {
                listValue = null
                index = 0
                return result as? ReturnStatement ?: this
            }

            index++

            if ((value.value.start + index * value.value.step) >= value.value.end) {
                listValue = null
                index = 0
                return this
            }

            throw IncompleteException()
        }

        throw RunException("Expression is not a list or a range")
    }
}
