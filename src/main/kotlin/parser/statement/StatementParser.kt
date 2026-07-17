package parser.statement

import parser.Parser
import parser.tokenizer.Type
import program.statement.Statement

interface StatementParser {
    fun parse(parser: Parser): Statement

    companion object {
        fun parse(parser: Parser): Statement {
            val token = parser.peekAllowNewline()

            return when (token.type) {
                Type.NEWLINE -> EmptyStatementParser().parse(parser)
                Type.FN_KEYWORD -> FunctionStatementParser().parse(parser)
                Type.IF_KEYWORD -> IfStatementParser().parse(parser)
                Type.FOR_KEYWORD -> ForStatementParser().parse(parser)
                Type.IMPORT_KEYWORD -> ImportStatementParser().parse(parser)
                Type.WHILE_KEYWORD -> WhileStatementParser().parse(parser)
                Type.DEL_KEYWORD -> DeleteStatementParser().parse(parser)
                Type.BREAK_KEYWORD -> BreakStatementParser().parse(parser)
                Type.CONTINUE_KEYWORD -> ContinueStatementParser().parse(parser)
                Type.RETURN_KEYWORD -> ReturnStatementParser().parse(parser)
                else -> ExpressionStatementParser().parse(parser)
            }
        }
    }
}
