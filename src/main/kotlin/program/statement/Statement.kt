package program.statement

import program.Program

interface Statement {
    fun execute(program: Program): Statement?
}
