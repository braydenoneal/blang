import program.Program

fun main() {
    Program.initialize()
    Program(
        """
        a = 0
        print(a)
        """.trimIndent(),
    ).run()
}
