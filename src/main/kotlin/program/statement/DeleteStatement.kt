package program.statement

import program.Program
import program.RunException

class DeleteStatement(val name: String) : Statement() {
    context(program: Program)
    override fun innerExecute(): Statement {
        program.scope.delete(name) ?: run { throw RunException("Variable with name '$name' does not exist", span) }
        return this
    }

    override fun toString(): String {
        return "del $name"
    }
}
