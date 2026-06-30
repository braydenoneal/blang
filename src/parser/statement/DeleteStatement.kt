package parser.statement

import parser.ParseException
import parser.Program
import parser.RunException
import tokenizer.Type

data class DeleteStatement(val name: String) : Statement {
    override fun execute(program: Program): Statement {
        program.scope.delete(name) ?: run { throw RunException("Variable with name '$name' does not exist") }
        return this
    }

    companion object {
        fun parse(program: Program): Statement {
            program.expect(Type.KEYWORD, "del")

            if (program.peek().type != Type.IDENTIFIER) {
                throw ParseException("Expression is not an identifier")
            }

            val name = program.next().value
            program.expect(Type.SEMICOLON)
            return DeleteStatement(name)
        }
    }
}
