package parser.statement

import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.statement.IfStatement
import program.statement.IfStatement.ElseIfStatement
import program.statement.IfStatement.ElseStatement
import program.statement.Statement
import program.statement.StatementList

class IfStatementParser : StatementParser {
    context(parser: Parser)
    override fun parse(spanStart: Int): Statement {
        val statements = StatementList()
        val elseIfStatements: MutableList<ElseIfStatement> = mutableListOf()
        var elseStatement: ElseStatement? = null
        val condition = ExpressionParser.parse()
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            statements.add(StatementParser.parse())
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)

        while (parser.peekIs(Type.ELIF_KEYWORD)) {
            elseIfStatements.add(parseElseIfStatement())
        }

        if (parser.peekIs(Type.ELSE_KEYWORD)) {
            elseStatement = parseElseStatement()
        }

        return IfStatement(condition, statements, elseIfStatements, elseStatement, null)
    }

    context(parser: Parser)
    fun parseElseIfStatement(): ElseIfStatement {
        val statements = StatementList()

        parser.expect(Type.ELIF_KEYWORD)
        val condition = ExpressionParser.parse()
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            statements.add(StatementParser.parse())
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)

        return ElseIfStatement(condition, statements, null)
    }

    context(parser: Parser)
    fun parseElseStatement(): ElseStatement {
        val statements = StatementList()

        parser.expect(Type.ELSE_KEYWORD)
        parser.expect(Type.LEFT_CURLY_BRACE)

        while (!parser.peekIs(Type.RIGHT_CURLY_BRACE)) {
            statements.add(StatementParser.parse())
        }

        parser.expect(Type.RIGHT_CURLY_BRACE)

        return ElseStatement(statements)
    }
}
