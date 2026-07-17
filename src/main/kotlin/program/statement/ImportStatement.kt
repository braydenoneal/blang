package program.statement

import program.Program

data class ImportStatement(val identifiers: MutableList<String>) : Statement {
    override fun execute(program: Program): Statement {
        program.addImport(this)
        return this
    }
}
