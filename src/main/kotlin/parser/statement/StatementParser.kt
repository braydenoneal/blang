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
            register(Type.FN_KEYWORD, FunctionStatementParser())
            register(Type.IF_KEYWORD, IfStatementParser())
            register(Type.FOR_KEYWORD, ForStatementParser())
            register(Type.IMPORT_KEYWORD, ImportStatementParser())
            register(Type.WHILE_KEYWORD, WhileStatementParser())
            register(Type.DEL_KEYWORD, DeleteStatementParser())
            register(Type.BREAK_KEYWORD, BreakStatementParser())
            register(Type.CONTINUE_KEYWORD, ContinueStatementParser())
            register(Type.RETURN_KEYWORD, ReturnStatementParser())
            register(Type.STRUCT_KEYWORD, StructStatementParser())
            register(Type.STATIC_KEYWORD, StaticStatementParser())
            register(Type.VAR_KEYWORD, StaticVariableStatementParser())
        }

        fun parse(parser: Parser): Statement {
            val token = parser.peek()
            val statementParser = statementParsers[token.type] ?: return expressionStatementParser.parse(parser)
            parser.next()
            return statementParser.parse(parser)
        }
    }
}
