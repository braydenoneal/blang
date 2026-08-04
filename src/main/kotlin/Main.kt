import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            print([0, "one", false, 0.1, { 0: 1 }, "two"])
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
