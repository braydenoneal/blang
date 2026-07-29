import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            a = "Hello\nWorld\n!"
            print(a.lines())
        """.trimIndent(),
    ).run(true)
}
