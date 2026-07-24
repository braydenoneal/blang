import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            print(2)
            a = print
            a(1)
        """.trimIndent(),
    ).run(true)
}
