import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            a = get: 0
            print(a)
        """.trimIndent(),
    ).run(true)
}
