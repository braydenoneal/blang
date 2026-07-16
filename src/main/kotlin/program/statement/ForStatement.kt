package program.statement

import parser.Parser
import parser.tokenizer.Type
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
    override fun execute(program: Program): Statement? {
        if (listValue == null) {
            val listResult = listExpression.evaluate(program) ?: return null
            listValue = listResult
        }

        val value = listValue

        if (value is ListValue) {
            val item = value.value[index]

            program.scope.set(itemName, item)
            val result = statements.runNext(program) ?: return null

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

            return null
        } else if (value is RangeValue) {
            val number = value.value.start + index * value.value.step

            program.scope.set(itemName, IntegerValue(number))
            val result = statements.runNext(program) ?: return null

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

            return null
        }

        throw RunException("Expression is not a list or a range")
    }

    companion object {
        fun parse(parser: Parser): Statement {
            val statements = StatementList()

            parser.expect(Type.KEYWORD, "for")
            val itemName = parser.expect(Type.IDENTIFIER)
            parser.expect(Type.KEYWORD, "in")
            val expression = Expression.parse(parser)
            parser.expect(Type.LEFT_CURLY_BRACE)

            while (!(parser.peekIs(Type.RIGHT_CURLY_BRACE))) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)
            return ForStatement(itemName, expression, statements)
        }
    }
}
