package program.statement

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.Program
import program.RunException

data class DeleteStatement(val name: String) : Statement {
    override fun execute(program: Program): Statement {
        program.scope.delete(name) ?: run { throw RunException("Variable with name '$name' does not exist") }
        return this
    }

    companion object {
        fun parse(parser: Parser): Statement {
            parser.expect(Type.KEYWORD, "del")

            if (parser.peek().type != Type.IDENTIFIER) {
                throw ParseException("Expression is not an identifier")
            }

            val name = parser.next().value
            parser.expectStatementEnd()
            return DeleteStatement(name)
        }
    }
}
