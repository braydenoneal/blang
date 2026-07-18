package parser.statement

import parser.Parser
import parser.tokenizer.Type
import program.statement.Statement

interface StatementParser {
    fun parse(parser: Parser): Statement

    companion object {
        val statementParsers: MutableMap<Type, StatementParser> = mutableMapOf()
        val expressionStatementParser = ExpressionStatementParser()

        fun register(type: Type, parser: StatementParser) {
            statementParsers[type] = parser
        }

        fun initialize() {
            register(Type.NEWLINE, EmptyStatementParser())
            register(Type.FN_KEYWORD, FunctionStatementParser())
            register(Type.IF_KEYWORD, IfStatementParser())
            register(Type.FOR_KEYWORD, ForStatementParser())
            register(Type.IMPORT_KEYWORD, ImportStatementParser())
            register(Type.WHILE_KEYWORD, WhileStatementParser())
            register(Type.DEL_KEYWORD, DeleteStatementParser())
            register(Type.BREAK_KEYWORD, BreakStatementParser())
            register(Type.CONTINUE_KEYWORD, ContinueStatementParser())
            register(Type.RETURN_KEYWORD, ReturnStatementParser())
        }

        fun parse(parser: Parser): Statement {
            val token = parser.peekAllowNewline()
            val statementParser = statementParsers[token.type] ?: return expressionStatementParser.parse(parser)
            parser.nextAllowNewline()
            return statementParser.parse(parser)
        }
    }
}
