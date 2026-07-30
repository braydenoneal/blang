package program.statement

import program.expression.value.util.StructDefinition

class StructStatement(val name: String, val struct: StructDefinition) : Statement() {
    override fun toString(): String {
        return "struct $struct"
    }
}
