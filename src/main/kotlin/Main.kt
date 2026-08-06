import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            [].size
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
