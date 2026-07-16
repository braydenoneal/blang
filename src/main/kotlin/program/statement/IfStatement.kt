package program.statement

import parser.Parser
import parser.tokenizer.Type
import program.Program
import program.expression.Expression
import program.expression.value.BooleanValue
import program.expression.value.Value

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
            val elseIfStatements: MutableList<ElseIfStatement> = mutableListOf()
            var elseStatement: ElseStatement? = null

            parser.expect(Type.IF_KEYWORD)
            val condition = Expression.parse(parser)
            parser.expect(Type.LEFT_CURLY_BRACE)

            while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
                statements.add(Statement.parse(parser))
            }

            parser.expect(Type.RIGHT_CURLY_BRACE)

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
