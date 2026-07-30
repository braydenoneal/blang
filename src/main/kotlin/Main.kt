import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            a = "Hello\nWorld\n!"
            print(a.lines())
            a = 0 + false
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
