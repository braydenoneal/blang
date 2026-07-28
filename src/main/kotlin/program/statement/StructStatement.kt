package program.statement

import program.Program
import program.expression.value.util.StructDefinition

class StructStatement(val name: String, val struct: StructDefinition) : Statement {
    override fun innerExecute(program: Program): Statement {
        return this
    }
}
