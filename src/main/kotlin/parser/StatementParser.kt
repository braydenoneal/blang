package parser

import parser.tokenizer.Type
import program.statement.*

object StatementParser {
    fun parse(parser: Parser): Statement {
        val token = parser.peekAllowNewline()

        if (token.type == Type.NEWLINE) {
            parser.nextAllowNewline()
            return EmptyStatement()
        }

        return when (token.type) {
            Type.FN_KEYWORD -> FunctionDeclaration.parse(parser)
            Type.IF_KEYWORD -> IfStatement.parse(parser)
            Type.FOR_KEYWORD -> ForStatement.parse(parser)
            Type.IMPORT_KEYWORD -> ImportStatement.parse(parser)
            Type.WHILE_KEYWORD -> WhileStatement.parse(parser)
            Type.DEL_KEYWORD -> DeleteStatement.parse(parser)
            Type.BREAK_KEYWORD -> BreakStatement.parse(parser)
            Type.CONTINUE_KEYWORD -> ContinueStatement.parse(parser)
            Type.RETURN_KEYWORD -> ReturnStatement.parse(parser)
            else -> ExpressionStatement.parse(parser)
        }
    }
}
