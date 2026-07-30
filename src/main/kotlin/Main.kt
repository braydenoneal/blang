import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            struct Test(a) {}
            
            a = Test(1)
            print(a.a)
            a.a = 2
            print(a.a)
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
