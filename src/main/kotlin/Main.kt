import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            a = 0 + 1
            b = !false
            
            for i in 0..3 {
                print(i)
            }
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
