import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            a = "1\"2\{3{2 + 2}5\"6\}7{4 + 4}9\"A\}B"
            b = "asdflkj"
            print(a)
            print(b)
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
