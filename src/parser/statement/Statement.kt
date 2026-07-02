package parser.statement

import parser.ParseException
import parser.Program
import tokenizer.Type

interface Statement {
    fun execute(program: Program): Statement?

    companion object {
        fun parse(program: Program): Statement {
            val token = program.peek()

            if (token.type == Type.KEYWORD) {
                when (token.value) {
                    "import" -> {
                        return ImportStatement.parse(program)
                    }

                    "fn" -> {
                        return FunctionDeclaration.parse(program)
                    }

                    "if" -> {
                        return IfStatement.parse(program)
                    }

                    "while" -> {
                        return WhileStatement.parse(program)
                    }

                    "for" -> {
                        return ForStatement.parse(program)
                    }

                    "del" -> {
                        return DeleteStatement.parse(program)
                    }

                    "break" -> {
                        return BreakStatement.parse(program)
                    }

                    "continue" -> {
                        return ContinueStatement.parse(program)
                    }

                    "return" -> {
                        return ReturnStatement.parse(program)
                    }
                }
            } else {
                return ExpressionStatement.parse(program)
            }

            throw ParseException("Unrecognized statement at $token")
        }
    }
}
