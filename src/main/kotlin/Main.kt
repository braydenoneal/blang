import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            a = "0123456"
            b = [0, 1, 2, 3, 4, 5, 6]
            print('{a[1]}, {a[4]}')
            print('{b[1]}, {b[4]}')
            print(a[1:4])
            print(b[1:4])
            print(a[4:1])
            print(b[4:1])
            print(a[:4])
            print(a[1:])
            print(a[:])
            print(b[:4])
            print(b[1:])
            print(b[:])
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
