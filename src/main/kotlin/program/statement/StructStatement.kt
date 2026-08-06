package program.statement

import program.expression.value.struct.StructDefinition

class StructStatement(val name: String, val struct: StructDefinition) : Statement() {
    override fun toString(): String {
        return "struct $struct"
    }
}
