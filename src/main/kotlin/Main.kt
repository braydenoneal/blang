import program.Program

fun main() {
    Program.initialize()
    Program(
        """
        """.trimIndent(),
    ).run(true)
}
