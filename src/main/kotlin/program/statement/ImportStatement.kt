package program.statement

import program.Program

class ImportStatement(
    val identifiers: MutableList<String>,
    val name: String,
) : Statement() {
    override fun innerExecute(program: Program): Statement {
        program.addImport(this)
        return this
    }

    override fun toString(): String {
        val string = "import ${identifiers.joinToString(".")}"

        if (name != identifiers.last()) {
            return "$string as $name"
        }

        return string
    }
}
