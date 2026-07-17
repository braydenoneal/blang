package program.statement

import program.Program
import program.RunException

data class DeleteStatement(val name: String) : Statement {
    override fun execute(program: Program): Statement {
        program.scope.delete(name) ?: run { throw RunException("Variable with name '$name' does not exist") }
        return this
    }
}
