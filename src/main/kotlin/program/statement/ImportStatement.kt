package program.statement

import program.Program

data class ImportStatement(
    val identifiers: MutableList<String>,
    val name: String,
) : Statement {
    override fun innerExecute(program: Program): Statement {
        program.addImport(this)
        return this
    }
}
