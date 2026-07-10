package parser.statement

import parser.Parser
import parser.Program
import parser.RunException
import parser.expression.Expression
import parser.expression.value.IntegerValue
import parser.expression.value.ListValue
import parser.expression.value.RangeValue
import parser.expression.value.Value
import tokenizer.Type

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
            parser.expect(Type.CURLY_BRACE, "{")

            while (!(parser.peekIs(Type.CURLY_BRACE, "}"))) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.CURLY_BRACE, "}")
            return ForStatement(itemName, expression, statements)
        }
    }
}
