import program.Program
import program.ProgramException

fun main() {
    Program.initialize()
    val program = Program(
        """
            a = {
                "a": 1,
                "b": 2,
                "c": 3,
            }
            
            print(a["a"])
            print(a["b"])
            print(a["c"])
            print(a.keys())
            print(a.values())
            print(a.entries())
            
            for entry in a {
                print(entry)
            }
        """.trimIndent(),
    )

    try {
        program.run(true)
    } catch (exception: ProgramException) {
        print(exception.getError(program))
    }
}
