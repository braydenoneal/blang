package parser.statement

import parser.ParseException
import parser.Parser
import parser.Program
import tokenizer.Type

interface Statement {
    fun execute(program: Program): Statement?

    companion object {
        fun parse(parser: Parser): Statement {
            val token = parser.peek()

            if (token.type == Type.KEYWORD) {
                when (token.value) {
                    "import" -> {
                        return ImportStatement.parse(parser)
                    }

                    "fn" -> {
                        return FunctionDeclaration.parse(parser)
                    }

                    "if" -> {
                        return IfStatement.parse(parser)
                    }

                    "while" -> {
                        return WhileStatement.parse(parser)
                    }

                    "for" -> {
                        return ForStatement.parse(parser)
                    }

                    "del" -> {
                        return DeleteStatement.parse(parser)
                    }

                    "break" -> {
                        return BreakStatement.parse(parser)
                    }

                    "continue" -> {
                        return ContinueStatement.parse(parser)
                    }

                    "return" -> {
                        return ReturnStatement.parse(parser)
                    }
                }
            } else {
                return ExpressionStatement.parse(parser)
            }

            throw ParseException("Unrecognized statement at $token")
        }
    }
}
