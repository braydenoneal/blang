package parser.statement

import parser.Parser
import parser.Program
import parser.expression.Expression
import parser.expression.value.BooleanValue
import parser.expression.value.Value
import tokenizer.Type

data class IfStatement(
    val condition: Expression,
    val statements: StatementList,
    val elseIfStatements: MutableList<ElseIfStatement>,
    val elseStatement: ElseStatement?,
    var conditionValue: Value<*>?,
) : Statement {
    override fun execute(program: Program): Statement? {
        if (conditionValue == null) {
            val conditionResult = condition.evaluate(program) ?: return null
            conditionValue = conditionResult
        }

        val value = conditionValue

        if (value is BooleanValue && value.value) {
            val result = statements.runNext(program) ?: return null
            reset()
            return result
        }

        for (elseIfStatement in elseIfStatements) {
            if (elseIfStatement.conditionValue == null) {
                val elseIfStatementConditionResult = elseIfStatement.condition.evaluate(program) ?: return null
                elseIfStatement.conditionValue = elseIfStatementConditionResult
            }

            val elseIfValue = elseIfStatement.conditionValue

            if (elseIfValue is BooleanValue && elseIfValue.value) {
                val result = elseIfStatement.statements.runNext(program) ?: return null
                reset()
                return result
            }
        }

        if (elseStatement == null) {
            reset()
            return this
        }

        val result = elseStatement.statements.runNext(program) ?: return null
        reset()
        return result
    }

    fun reset() {
        conditionValue = null

        for (elseIfStatement in elseIfStatements) {
            elseIfStatement.conditionValue = null
        }
    }

    companion object {
        fun parse(parser: Parser): Statement {
            val statements = StatementList()
            val elseIfStatements: MutableList<ElseIfStatement> = ArrayList()
            var elseStatement: ElseStatement? = null

            parser.expect(Type.KEYWORD, "if")
            val condition = Expression.parse(parser)
            parser.expect(Type.CURLY_BRACE, "{")

            while (!parser.peekIs(Type.CURLY_BRACE, "}")) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.CURLY_BRACE, "}")

            while (parser.peekIs(Type.KEYWORD, "elif")) {
                elseIfStatements.add(ElseIfStatement.parse(parser))
            }

            if (parser.peekIs(Type.KEYWORD, "else")) {
                elseStatement = ElseStatement.parse(parser)
            }

            return IfStatement(condition, statements, elseIfStatements, elseStatement, null)
        }
    }
}
