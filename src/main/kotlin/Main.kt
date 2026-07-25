import program.Program

fun main() {
    Program.initialize()
    Program(
        """
            for i in 0..10 {
                print(i)
            }
        """.trimIndent(),
    ).run(true)
}
